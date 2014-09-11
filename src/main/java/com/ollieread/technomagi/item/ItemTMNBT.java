package com.ollieread.technomagi.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ItemTMNBT extends ItemTM
{

    public ItemTMNBT(String name)
    {
        super(name);
    }

    public static NBTTagCompound getNBT(ItemStack stack)
    {
        return stack.stackTagCompound;
    }

    public static ItemStack resetNBT(ItemStack stack)
    {
        stack.stackTagCompound = new NBTTagCompound();

        return stack;
    }

}
