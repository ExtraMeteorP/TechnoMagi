package com.ollieread.technomagi.common.item.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
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
    public IIcon itemOverlayIcon;

    public ItemEntityCapture(String name)
    {
        super(name);

        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
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
                    ItemStack newStack = new ItemStack(this, 1, 1);
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

                        stack.stackSize--;
                    }
                }
            }
        }

        return stack;
    }

}
