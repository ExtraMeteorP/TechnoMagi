package com.ollieread.technomagi.tileentity;

import net.minecraft.item.ItemStack;

public interface IDisguisableTile
{

    public boolean isDisguised();

    public boolean setDisguise(ItemStack stack);

    public ItemStack getDisguise();

}
