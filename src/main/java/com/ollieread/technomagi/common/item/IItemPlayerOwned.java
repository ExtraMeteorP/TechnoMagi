package com.ollieread.technomagi.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IItemPlayerOwned
{
    public String getPlayer(ItemStack stack);

    public boolean isPlayer(ItemStack stack, EntityPlayer player);

    public boolean isPlayer(ItemStack stack, String player);

    public void setPlayer(ItemStack stack, EntityPlayer player);

    public void setPlayer(ItemStack stack, String player);

}
