package com.ollieread.technomagi.common.item.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.input.Keyboard;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;
import com.ollieread.technomagi.util.EntityHelper;
import com.ollieread.technomagi.util.ItemNBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEntityCapture extends ItemEntityBase
{

    @SideOnly(Side.CLIENT)
    public IIcon[] itemIcons;

    public ItemEntityCapture(String name)
    {
        super(name);

        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcons = new IIcon[4];
        itemIcons[0] = register.registerIcon(getTexturePath("capture"));
        itemIcons[1] = register.registerIcon(getTexturePath("capture/full"));
        itemIcons[2] = register.registerIcon(getTexturePath("capture/reusable"));
        itemIcons[3] = register.registerIcon(getTexturePath("capture/reusable_full"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
        if (stack.getItemDamage() == 0) {
            if (hasEntity(stack)) {
                return itemIcons[1];
            }

            return itemIcons[0];
        } else {
            if (hasEntity(stack)) {
                return itemIcons[3];
            }

            return itemIcons[2];
        }
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        return getIconIndex(stack);
    }

    @Override
    public boolean isEntityValid(ItemStack stack, EntityLivingBase entity)
    {
        return !entity.isDead && EntityHelper.canBeCaptured(entity.getClass());
    }

    @Override
    public boolean isEntityValid(ItemStack stack, Class entity)
    {
        return EntityHelper.canBeCaptured(entity);
    }

    @Override
    public void setEntity(ItemStack stack, EntityLivingBase entity)
    {
        NBTTagCompound entityCompound = new NBTTagCompound();
        entity.writeToNBT(entityCompound);

        ItemNBTHelper.setString(stack, "Entity", EntityHelper.getEntityName(entity.getClass()));
        ItemNBTHelper.setCompound(stack, "EntityData", entityCompound);
    }

    public EntityLivingBase getEntityObject(ItemStack stack, World world)
    {
        String entity = getEntity(stack);

        if (entity != null) {
            EntityLivingBase entityLiving = (EntityLivingBase) EntityList.createEntityByName(entity, world);

            if (entityLiving != null) {
                entityLiving.readEntityFromNBT(ItemNBTHelper.getCompound(stack, "EntityData"));

                return entityLiving;
            }
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced)
    {
        super.addInformation(stack, player, info, advanced);

        if (!getEntity(stack).isEmpty() && getEntity(stack) != null) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                EntityLivingBase entity = getEntityObject(stack, player.worldObj);

                if (entity != null) {
                    IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(entity.getClass());

                    info.add("Health: " + EnumChatFormatting.ITALIC + (int) entity.getHealth());
                    info.add("Child: " + EnumChatFormatting.ITALIC + (entity.isChild() ? "Yes" : "No"));
                    info.add("Hostile: " + EnumChatFormatting.ITALIC + (descriptor.isMonster() ? "Yes" : "No"));
                    info.add("Undead: " + EnumChatFormatting.ITALIC + (descriptor.isUndead() ? "Yes" : "No"));
                }
            } else {
                info.add("Hold " + EnumChatFormatting.DARK_AQUA + "SHIFT" + EnumChatFormatting.GRAY + " for more details.");
            }
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        if (!hasEntity(stack)) {
            if (entity instanceof EntityLiving && !(entity instanceof IBossDisplayData)) {
                IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(entity.getClass());

                if (descriptor != null && descriptor.canBeCaptured()) {
                    ItemStack newStack = new ItemStack(this, 1, stack.getItemDamage());
                    newStack.stackTagCompound = new NBTTagCompound();
                    setEntity(newStack, entity);
                    player.inventory.addItemStackToInventory(newStack);
                    stack.stackSize--;
                    entity.setDead();

                    return true;
                }
            }
        }

        return super.itemInteractionForEntity(stack, player, entity);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (hasEntity(stack)) {
            String entityName = getEntity(stack);

            if (entityName != null) {
                EntityLivingBase entityLiving = getEntityObject(stack, world);

                if (entityLiving != null) {
                    MovingObjectPosition mouse = Minecraft.getMinecraft().objectMouseOver;

                    if (mouse.typeOfHit.equals(MovingObjectType.BLOCK)) {
                        ForgeDirection dir = ForgeDirection.getOrientation(mouse.sideHit);
                        entityLiving.setPosition(mouse.blockX + dir.offsetX, mouse.blockY + dir.offsetY, mouse.blockZ + dir.offsetZ);

                        if (!world.isRemote) {
                            world.spawnEntityInWorld(entityLiving);
                        }

                        if (stack.getItemDamage() == 1) {
                            ItemNBTHelper.resetNBT(stack);
                        } else {
                            stack.stackSize--;
                        }
                    }
                }
            }
        }

        return stack;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack emptyStack1 = new ItemStack(item, 1, 0);
        ItemStack emptyStack2 = new ItemStack(item, 1, 1);

        list.add(emptyStack1);
        list.add(emptyStack2);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return "item.technomagi." + this.name + (stack.getItemDamage() == 1 ? ".reusable" : "");
    }

}
