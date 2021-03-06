package com.ollieread.technomagi.item;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.ollieread.ennds.extended.ExtendedNanites;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNaniteContainer extends ItemTMNBT
{

    @SideOnly(Side.CLIENT)
    protected IIcon itemIconFull;

    public ItemNaniteContainer(String name)
    {
        super(name);

        setMaxStackSize(64);
        setHasSubtypes(true);
    }

    public static String getEntity(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound.hasKey("Entity")) {
            return compound.getString("Entity");
        }

        return null;
    }

    public static String getPlayer(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound.hasKey("Entity")) {
            if (compound.getString("Entity") == "player") {
                return compound.getString("PlayerName");
            }
        }

        return null;
    }

    public static void setEntity(ItemStack stack, EntityLivingBase entity)
    {
        NBTTagCompound compound = getNBT(stack);
        String entityName;

        if (entity instanceof EntityPlayer) {
            entityName = "player";
            compound.setString("PlayerName", ((EntityPlayer) entity).getCommandSenderName());
            stack.setItemDamage(1);
        } else {
            setEntity(stack, entity.getClass());
            return;
        }

        compound.setString("Entity", entityName);
    }

    public static void setEntity(ItemStack stack, Class entityClass)
    {
        NBTTagCompound compound = getNBT(stack);
        String entityName;

        entityName = (String) EntityList.classToStringMapping.get(entityClass);
        Set<Class> entities = ResearchRegistry.getMonitorableEntities();
        stack.setItemDamage(2);

        compound.setString("Entity", entityName);
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + (getEntity(stack) != null ? ".full.name" : ".name"));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        String info = "";

        String entityName = getEntity(stack);
        Class entityClass = (Class) EntityList.stringToClassMapping.get(entityName);

        if (entityName != null) {
            if (entityClass != null) {
                info = StatCollector.translateToLocal("entity." + entityName + ".name");
            } else if (entityName.equals("player")) {
                info = EnumChatFormatting.DARK_PURPLE + getPlayer(stack);
            }

            list.add(info);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString());
        itemIconFull = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString() + "Full");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int d)
    {
        return d == 0 ? itemIcon : itemIconFull;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
        String entityName = this.getEntity(stack);

        if (entityName != null) {
            return itemIconFull;
        }

        return itemIcon;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack empty = new ItemStack(item, 1, 0);
        resetNBT(empty);
        list.add(empty);

        ItemStack player = new ItemStack(item, 1, 1);
        resetNBT(player);
        setEntity(player, TechnoMagi.proxy.getClientPlayer());
        list.add(player);

        Iterator iterator = ResearchRegistry.getMonitorableEntities().iterator();
        int x = 2;

        while (iterator.hasNext()) {
            Class entityClass = (Class) iterator.next();

            ItemStack stack = new ItemStack(item, 1, x);
            stack.stackTagCompound = new NBTTagCompound();
            this.setEntity(stack, entityClass);

            list.add(stack);
            x++;
        }
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

        if (charon != null && !charon.canSpecialise()) {
            if (stack.getItemDamage() == 0) {
                if (charon.nanites.decreaseNanites(10)) {
                    ItemStack newStack = new ItemStack(this, 1, 1);
                    newStack.stackTagCompound = new NBTTagCompound();
                    this.setEntity(newStack, player);
                    player.inventory.addItemStackToInventory(newStack);
                    stack.stackSize--;
                }
            } else if (stack.getItemDamage() == 1 && getEntity(stack).equals("player")) {
                if (charon.nanites.increaseNanites(10)) {
                    stack.stackSize--;
                }
            }
        }

        return stack;
    }

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        if (stack.getItemDamage() == 0) {
            if (entity instanceof EntityPlayer && !entity.equals(player)) {
                ItemStack newStack = new ItemStack(this, 1, 1);
                newStack.stackTagCompound = new NBTTagCompound();
                this.setEntity(newStack, player);
                player.inventory.addItemStackToInventory(newStack);
                stack.stackSize--;
            }
        } else {
            EntityLiving entityLiving = (EntityLiving) entity;
            String entityName = (String) EntityList.classToStringMapping.get(entityLiving.getClass());

            if (getEntity(stack).equals(entityName)) {
                ExtendedNanites nanites = ExtendedNanites.get(entityLiving);

                if (nanites != null && (nanites.getOwner() == null || nanites.getOwner().equals("none"))) {
                    if (!player.worldObj.isRemote) {
                        nanites.setOwner(player);
                        nanites.setNanites(10);

                        if (!entityLiving.hasCustomNameTag()) {
                            entityLiving.setCustomNameTag("Subject " + entityLiving.getEntityId());
                            entityLiving.func_110163_bv();
                        }

                        entityLiving.setAttackTarget(null);

                        stack.stackSize--;
                    }
                }
                return true;
            }
        }

        return super.itemInteractionForEntity(stack, player, entity);
    }
}
