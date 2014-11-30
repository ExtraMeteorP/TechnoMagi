package com.ollieread.technomagi.common.proxy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class BasicEnergy implements IEnergyHandler
{

    protected EnergyStorage storage;

    public BasicEnergy(int capacity)
    {
        this(capacity, capacity, capacity);
    }

    public BasicEnergy(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer);
    }

    public BasicEnergy(int capacity, int maxReceive, int maxExtract)
    {
        storage = new EnergyStorage(capacity, maxReceive, maxExtract);
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        storage.readFromNBT(nbt);
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        storage.writeToNBT(nbt);
    }

    /* IEnergyHandler */
    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return true;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return storage.getMaxEnergyStored();
    }

    public boolean modifyEnergyStored(int energy)
    {
        int c = getEnergyStored(null);
        storage.modifyEnergyStored(-energy);

        if ((c - energy) == getEnergyStored(null)) {
            return true;
        }

        return false;
    }

    public void increaseEnergyStored(int energy)
    {
        int capacity = storage.getEnergyStored();

        if (capacity < storage.getMaxEnergyStored() && (capacity + energy) <= storage.getMaxEnergyStored()) {
            storage.setEnergyStored(capacity + energy);
        }
    }

    public int getMaxReceive()
    {
        return storage.getMaxReceive();
    }

    public int getMaxExtract()
    {
        return storage.getMaxExtract();
    }

}
