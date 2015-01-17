package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public interface ITileEntityGui
{

    public Container getContainer(InventoryPlayer playerInventory, World world, int x, int y, int z);

    public int getGui(World world, int x, int y, int z, EntityPlayer player);

}
