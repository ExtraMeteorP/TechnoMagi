package com.ollieread.technomagi.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.ollieread.ennds.item.IStaff;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.common.proxy.ItemBasicInventory;
import com.ollieread.technomagi.common.proxy.ItemBasicInventory.WithInventory;
import com.ollieread.technomagi.util.InventoryHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStaff extends ItemTMNBT implements IStaff
{

    public ItemStaff(String name)
    {
        super(name);

        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {

    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        resetNBT(stack);
    }

    public static void setPlayer(ItemStack stack, String player)
    {
        NBTTagCompound compound = getNBT(stack);

        compound.setString("Player", player);
    }

    public static String getPlayer(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        return compound.getString("Player");
    }

    public void setPlayer(ItemStack stack, EntityPlayer player)
    {
        setPlayer(stack, player.getCommandSenderName());
    }

    public boolean isPlayer(ItemStack staff, EntityPlayer player)
    {
        String name = getPlayer(staff);

        return name != null && name.equals(player.getCommandSenderName());
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + (stack.getItemDamage() == 1 ? ".full.name" : ".name"));
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        list.add(resetNBT(new ItemStack(item, 1, 0)));
    }

    @Override
    public boolean hasEnhancement(ItemStack staff, String enhancement, int level)
    {
        NBTTagCompound compound = getNBT(staff);

        if (compound.hasKey("Enhancements")) {
            NBTTagList enhancements = compound.getTagList("Enhancements", compound.getId());

            for (int i = 0; i < enhancements.tagCount(); i++) {
                NBTTagCompound tag = enhancements.getCompoundTagAt(i);

                if (tag.getString("Enhancement") != null) {
                    return tag.getString("Enhancement").equals(enhancement) && tag.getInteger("Level") == level;
                }
            }
        }
        return false;
    }

    @Override
    public void setEnhancement(ItemStack staff, String enhancement, int level)
    {
        NBTTagCompound compound = getNBT(staff);

        if (compound.hasKey("Enhancements")) {
            NBTTagList enhancements = compound.getTagList("Enhancements", compound.getId());
            NBTTagCompound entry = new NBTTagCompound();
            entry.setString("Enhancement", enhancement);
            entry.setInteger("Level", level);
            enhancements.appendTag(entry);

            compound.setTag("Enhancements", enhancements);
        }
    }

    @Override
    public int getCurrentCast(ItemStack staff)
    {
        NBTTagCompound compound = getNBT(staff);

        if (compound.hasKey("CastTime")) {
            return compound.getInteger("CastTime");
        }

        return 0;
    }

    @Override
    public void setCurrentCast(ItemStack staff, int cast)
    {
        NBTTagCompound compound = getNBT(staff);

        compound.setInteger("CastTime", cast);
    }

    public static IInventory getInventory(ItemStack staff)
    {
        if (staff.getItemDamage() == 0) {
            return new ItemBasicInventory(staff, 4);
        }

        return null;
    }

    public static IInventory getInventory(ItemStack staff, IInventory playerInventory, int slot)
    {
        if (staff.getItemDamage() == 0) {
            return new WithInventory(playerInventory, slot, 4);
        }

        return null;
    }

    public static boolean isComplete(ItemStack staff)
    {
        if (staff.getItemDamage() == 1) {
            return true;
        }

        IInventory inventory = getInventory(staff);

        if (inventory != null) {
            if (InventoryHelper.hasInventoryItem(inventory, new ItemStack(Items.itemEtherium)) && InventoryHelper.hasInventoryItem(inventory, new ItemStack(net.minecraft.init.Items.iron_ingot, 2)) && InventoryHelper.hasInventoryItem(inventory, new ItemStack(net.minecraft.init.Items.gold_ingot)) && InventoryHelper.hasInventoryItem(inventory, new ItemStack(net.minecraft.init.Items.redstone, 4))) {
                return true;
            }
        }

        return false;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        setCurrentCast(stack, getCurrentCast(stack) + 1);

        return true;
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int i)
    {
        setCurrentCast(stack, 0);
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.bow;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (stack.getItemDamage() == 0) {
            // if (isPlayer(stack, player)) {
            player.openGui(TechnoMagi.instance, CommonProxy.GUI_STAFF, world, 0, 0, 0);
            // }
        }

        return stack;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("Enhancements:");
        NBTTagCompound compound = getNBT(stack);

        if (compound.hasKey("Enhancements")) {
            NBTTagList enhancements = compound.getTagList("Enhancements", compound.getId());

            for (int i = 0; i < enhancements.tagCount(); i++) {
                NBTTagCompound tag = enhancements.getCompoundTagAt(i);
                String enhancement = tag.getString("Enhancement");

                if (enhancement != null) {
                    list.add(StatCollector.translateToLocal("enhancement." + enhancement));
                }
            }
        }
    }
}
