package com.ollieread.technomagi.common.block.battery.tile;

import java.util.Arrays;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.lib.util.helpers.EnergyHelper;

import com.ollieread.technomagi.common.block.tile.TileEnergy;

public class TileBattery extends TileEnergy
{

    protected EnergyStorage energy;

    public TileBattery()
    {
        super(0);
    }

    public TileBattery(int capacity, int maxReceive, int maxExtract)
    {
        super(capacity, maxReceive, maxExtract);
    }

    protected void distribute()
    {
        for (ForgeDirection direction : Arrays.asList(ForgeDirection.VALID_DIRECTIONS)) {
            TileEntity tile = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);

            if (tile instanceof IEnergyReceiver) {
                if (EnergyHelper.isAdjacentEnergyReceiverFromSide(tile, direction.getOpposite().ordinal())) {

                }
            }
        }
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            distribute();
        }
    }

}
