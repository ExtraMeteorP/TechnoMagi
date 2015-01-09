package com.ollieread.technomagi.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.item.IStaff;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.proxy.ItemBasicInventory;
import com.ollieread.technomagi.common.proxy.ItemBasicInventory.WithInventory;
import com.ollieread.technomagi.util.InventoryHelper;
import com.ollieread.technomagi.util.ItemHelper;

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

    public EnumRarity getRarity(ItemStack stack)
    {
        if (stack.getItemDamage() == 1) {
            return EnumRarity.epic;
        }

        return super.getRarity(stack);
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
            if (InventoryHelper.hasInventoryItem(inventory, ItemHelper.resource("etheriumCyrstal", 1)) && InventoryHelper.hasInventoryItem(inventory, ItemHelper.item("iron_ingot", 2)) && InventoryHelper.hasInventoryItem(inventory, ItemHelper.item("gold_ingot")) && InventoryHelper.hasInventoryItem(inventory, ItemHelper.item("redstone", 4))) {
                return true;
            }
        }

        return false;
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
        } else {
            ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(player);

            if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
                player.setItemInUse(stack, 800);
            }
        }

        return stack;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return true;
    }

    @Override
    public boolean canCast()
    {
        return true;
    }

}
