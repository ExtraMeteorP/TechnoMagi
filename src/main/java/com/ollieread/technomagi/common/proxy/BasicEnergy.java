package com.ollieread.technomagi.common.proxy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyStorage;

public class BasicEnergy implements IEnergyStorage, IEnergyConnection
{

    protected EnergyStorage energy;

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
        NBTTagCompound energyStorage = compound.getCompoundTag("EnergyStorage");

        energy.readFromNBT(energyStorage);
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound energyStorage = new NBTTagCompound();

        energy.writeToNBT(energyStorage);

        compound.setTag("EnergyStorage", energyStorage);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return true;
    }

    public int getMaxReceive()
    {
        return energy.getMaxReceive();
    }

    public int getMaxExtract()
    {
        return energy.getMaxExtract();
    }

    public boolean modifyEnergyStored(int energy)
    {
        int c = getEnergyStored();
        this.energy.modifyEnergyStored(-energy);

        if ((c - 5) == getEnergyStored()) {
            return true;
        }

        return false;
    }

}
