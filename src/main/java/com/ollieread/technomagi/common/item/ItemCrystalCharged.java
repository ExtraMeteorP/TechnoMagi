package com.ollieread.technomagi.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.util.ItemNBTHelper;
import com.ollieread.technomagi.util.ItemStackHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrystalCharged extends ItemSubtypes
{

    public ItemCrystalCharged(String name)
    {
        super(name, new String[] { "etherium", "void", "noughtite", "relux", "cimmerium", "calidite", "glacite", "verux", "oblerite" });
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return getEnergy(stack) < getMaxDamage(stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        return 500;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1 - (1D / getMaxDamage(stack)) * getEnergy(stack);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        ItemNBTHelper.resetNBT(stack);
    }

    public void setEnergy(ItemStack stack, int energy)
    {
        ItemNBTHelper.setInteger(stack, "Energy", energy);
    }

    public int getEnergy(ItemStack stack)
    {
        return ItemNBTHelper.getInteger(stack, "Energy");
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
    {
        return false;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        ItemStack copied = stack.copy();

        if (getEnergy(copied) > 5) {
            setEnergy(copied, getEnergy(copied) - 5);
            return copied;
        } else {
            String type = this.getName(stack.getItemDamage());
            return ItemStackHelper.itemSubtype(Items.resource, type + "_dust", 1);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < this.names.length; i++) {
            ItemStack stack = new ItemStack(item, 1, i);
            setEnergy(stack, 500);

            list.add(stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced)
    {
        super.addInformation(stack, player, info, advanced);

        info.add("Energy: " + getEnergy(stack));
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entity)
    {
        ItemStack stack = entity.getEntityItem();
        if (entity.isInWater() && stack.getItemDamage() == getDamageFromName("glacite_charged")) {

        }

        return false;
    }

}
