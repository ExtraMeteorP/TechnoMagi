package com.ollieread.technomagi.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

import com.ollieread.technomagi.common.proxy.BasicEnergy;

public class TileEntityGeneratorBasic extends TileEntityTM implements IEnergyHandler
{

    protected BasicEnergy storage = new BasicEnergy(5120, 0, 10);

    protected int energyGeneration = 0;

    public void setGenerationByLocation(int y)
    {
        int generation = (int) Math.ceil((256 - y) / (256 / 5));

        energyGeneration = generation;
    }

    public void updateEntity()
    {

    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        energyGeneration = compound.getInteger("EnergyGeneration");

        storage.readFromNBT(compound);
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("EnergyGeneration", energyGeneration);

        storage.writeToNBT(compound);
    }

    /* Everything below is just a proxy for the interfaces */

    /* ENERGY */

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return storage.canConnectEnergy(from);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        return storage.receiveEnergy(ForgeDirection.DOWN, maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        return storage.extractEnergy(ForgeDirection.DOWN, maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return storage.getEnergyStored(null);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return storage.getMaxEnergyStored(null);
    }

}
