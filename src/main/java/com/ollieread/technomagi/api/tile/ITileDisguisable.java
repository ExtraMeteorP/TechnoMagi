package com.ollieread.technomagi.api.tile;

import net.minecraft.item.ItemStack;

public interface ITileDisguisable
{

    public boolean isDisguised();

    public ItemStack getDisguise();

    public boolean setDisguiseBlock(ItemStack stack);

    public void removeDisguise();

}
