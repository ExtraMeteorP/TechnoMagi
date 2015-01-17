package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ITileEntityBlockHandler
{

    public void placed(EntityLivingBase entity, ItemStack stack);

    public boolean activated(EntityPlayer player, int side, float hitX, float hitY, float hitZ);

}
