package com.ollieread.technomagi.api.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemNBTHelper
{

    public static NBTTagCompound getNBT(ItemStack stack)
    {
        if (stack.stackTagCompound == null) {
            resetNBT(stack);
        }

        return stack.stackTagCompound;
    }

    public static void resetNBT(ItemStack stack)
    {
        stack.stackTagCompound = new NBTTagCompound();
    }

    public static boolean has(ItemStack stack, String key)
    {
        NBTTagCompound compound = getNBT(stack);

        return compound.hasKey(key);
    }

    public static void setString(ItemStack stack, String key, String value)
    {
        getNBT(stack).setString(key, value);
    }

    public static String getString(ItemStack stack, String key)
    {
        if (has(stack, key)) {
            return getNBT(stack).getString(key);
        }

        return null;
    }

    public static void setInteger(ItemStack stack, String key, int value)
    {
        getNBT(stack).setInteger(key, value);
    }

    public static int getInteger(ItemStack stack, String key)
    {
        if (has(stack, key)) {
            return getNBT(stack).getInteger(key);
        }

        return -1;
    }

    public static void setCompound(ItemStack stack, String key, NBTTagCompound value)
    {
        getNBT(stack).setTag(key, value);
    }

    public static NBTTagCompound getCompound(ItemStack stack, String key)
    {
        if (has(stack, key)) {
            return getNBT(stack).getCompoundTag(key);
        }

        return null;
    }
}
