package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.abstracts.TileEntityMachine;

public class TileEntityMachineInfuserCharger extends TileEntityMachine
{

    public TileEntityMachineInfuserCharger()
    {
        super(0);
    }

    @Override
    public Container getContainer(InventoryPlayer playerInventory, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public int getGui(World world, int x, int y, int z, EntityPlayer player)
    {
        return -1;
    }

    @Override
    public boolean canProcess()
    {
        return false;
    }

    @Override
    public boolean isProcessing()
    {
        return false;
    }

    @Override
    public void process()
    {

    }

}
