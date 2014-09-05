package com.ollieread.technomagi.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyStorage;

public class TileEntityPower extends TileEntityTM implements IEnergyStorage
{

    protected EnergyStorage energy;

    public TileEntityPower(int capacity)
    {
        this(capacity, capacity, capacity);
    }

    public TileEntityPower(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer);
    }

    public TileEntityPower(int capacity, int maxReceive, int maxExtract)
    {
        energy = new EnergyStorage(capacity, maxReceive, maxExtract);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        return energy.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        return energy.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored()
    {
        return energy.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored()
    {
        return energy.getMaxEnergyStored();
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        NBTTagCompound energyStorage = compound.getCompoundTag("EnergyStorage");

        energy.readFromNBT(energyStorage);
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagCompound energyStorage = new NBTTagCompound();

        energy.writeToNBT(energyStorage);

        compound.setTag("EnergyStorage", energyStorage);
    }

}
