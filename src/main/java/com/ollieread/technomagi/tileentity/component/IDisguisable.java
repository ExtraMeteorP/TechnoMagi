package com.ollieread.technomagi.tileentity.component;

import net.minecraft.item.ItemStack;

public interface IDisguisable
{

    public boolean isDisguised();

    public boolean setDisguise(ItemStack stack);

    public ItemStack getDisguise();

}
