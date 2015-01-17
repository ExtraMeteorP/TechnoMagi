package com.ollieread.technomagi.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

import com.ollieread.technomagi.tileentity.abstracts.Basic;
import com.ollieread.technomagi.tileentity.component.Power;
import com.ollieread.technomagi.util.PowerHelper;

public class TileEntityBattery extends Basic implements IEnergyHandler
{

    protected Power energy = null;

    public TileEntityBattery()
    {
        new Power(0, 0, 0);
    }

    public TileEntityBattery(int capacity, int maxInput, int maxExtract)
    {
        energy = new Power(capacity, maxInput, maxExtract);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        energy.readFromNBT(compound);
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        energy.writeToNBT(compound);
    }

    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            PowerHelper.pushToAdjacent(this, ForgeDirection.NORTH);
            PowerHelper.pushToAdjacent(this, ForgeDirection.SOUTH);
            PowerHelper.pushToAdjacent(this, ForgeDirection.EAST);
            PowerHelper.pushToAdjacent(this, ForgeDirection.WEST);
        }
    }

    /* Everything below is just a proxy for the interfaces */

    /* ENERGY */

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return true;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        if (from.equals(ForgeDirection.UP) || from.equals(ForgeDirection.DOWN)) {
            int r = energy.receiveEnergy(from, maxReceive, simulate);

            if (r > 0) {
                sync();
                return r;
            }
        }

        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        if (!from.equals(ForgeDirection.UP) && !from.equals(ForgeDirection.DOWN)) {
            int r = energy.extractEnergy(from, maxExtract, simulate);

            if (r > 0) {
                if (simulate) {
                    sync();
                }

                return r;
            }
        }

        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return energy.getEnergyStored(null);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return energy.getMaxEnergyStored(null);
    }

    public int getMaxReceive()
    {
        return energy.getMaxReceive();
    }

    public int getMaxExtract()
    {
        return energy.getMaxExtract();
    }

}
