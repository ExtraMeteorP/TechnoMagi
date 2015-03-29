package com.ollieread.technomagi.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import com.ollieread.technomagi.client.gui.window.abstracts.Window;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface ITileGui
{

    public Container getContainer(EntityPlayer player);

    @SideOnly(Side.CLIENT)
    public Window getWindow(EntityPlayer player);

}
