package com.ollieread.technomagi.common.item;

import net.minecraft.item.ItemStack;

public interface IItemPlayerLocked extends IItemPlayerOwned
{

    public boolean isPlayerLocked(ItemStack stack);

}
