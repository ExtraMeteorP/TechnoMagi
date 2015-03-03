package com.ollieread.technomagi.common.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;

public class TileEnergy extends TileBase implements IEnergyReceiver
{

    protected EnergyStorage energy;

    public TileEnergy(int capacity)
    {
        this(capacity, capacity, capacity);
    }

    public TileEnergy(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer);
    }

    public TileEnergy(int capacity, int maxReceive, int maxExtract)
    {
        energy = new EnergyStorage(capacity, maxReceive, maxExtract);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return from.equals(ForgeDirection.UP) || from.equals(ForgeDirection.DOWN);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        int r = energy.receiveEnergy(maxReceive, simulate);

        if (r > 0) {
            sync();
            return r;
        }

        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return energy.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return energy.getMaxEnergyStored();
    }

    public void setCapacity(int capacity)
    {
        this.energy.setCapacity(capacity);
    }

    public int getMaxReceive()
    {
        return energy.getMaxReceive();
    }

    public void setMaxReceive(int maxReceive)
    {
        this.energy.setMaxReceive(maxReceive);
    }

    public int getMaxExtract()
    {
        return energy.getMaxExtract();
    }

    public void setMaxExtract(int maxExtract)
    {
        this.energy.setMaxExtract(maxExtract);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        NBTTagCompound energyCompound = compound.getCompoundTag("Energy");
        energy = new EnergyStorage(energyCompound.getInteger("Capacity"));
        energy.readFromNBT(energyCompound);
    }

    public void setEnergyStored(int energy)
    {
        this.energy.setEnergyStored(energy);
    }

    public void modifyEnergyStored(int energy)
    {
        this.energy.modifyEnergyStored(energy);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagCompound energyCompound = new NBTTagCompound();

        energy.writeToNBT(energyCompound);
        energyCompound.setInteger("Capacity", energy.getMaxEnergyStored());
        compound.setTag("Energy", energyCompound);
    }
}
