package com.ollieread.technomagi.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class ItemTMNBT extends ItemTM
{

    public ItemTMNBT(String name)
    {
        super(name);
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {

    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        stack.stackTagCompound = new NBTTagCompound();
    }

    public static NBTTagCompound getNBT(ItemStack stack)
    {
        if (stack.stackTagCompound == null) {
            resetNBT(stack);
        }

        return stack.stackTagCompound;
    }

    public static ItemStack resetNBT(ItemStack stack)
    {
        stack.stackTagCompound = new NBTTagCompound();

        return stack;
    }

    public static String getNBTString(ItemStack stack, String name)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound.hasKey(name)) {
            return compound.getString(name);
        }

        return null;
    }

}
