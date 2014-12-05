package com.ollieread.technomagi.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.init.Items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabTMCircuit extends CreativeTabs
{

    public CreativeTabTMCircuit()
    {
        super("tabTmCircuit");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack()
    {
        ItemStack stack = new ItemStack(Items.itemMalleableCircuit);
        stack.stackTagCompound = new NBTTagCompound();
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
