package com.ollieread.technomagi.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyStorage;

import com.ollieread.technomagi.tileentity.proxy.BasicEnergy;

public class TileEntityGeneratorBasic extends TileEntityTM implements IEnergyStorage, IEnergyConnection
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
        if (getEnergyStored() < getMaxEnergyStored()) {
            storage.receiveEnergy(energyGeneration, false);
        }
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
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored()
    {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored()
    {
        return storage.getMaxEnergyStored();
    }

}
