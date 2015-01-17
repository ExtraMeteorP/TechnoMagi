package com.ollieread.technomagi.tileentity.abstracts;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

import com.ollieread.technomagi.tileentity.component.Power;

public abstract class Generator extends Basic implements IEnergyHandler
{

    protected Power energy = null;
    public int energyGeneration = 0;
    public int energyTicks = 0;
    public int working = 0;

    public Generator(int capacity, int maxExtract, int generation, int ticks)
    {
        energy = new Power(capacity, 0, maxExtract);
        energyGeneration = generation;
        energyTicks = ticks;
    }

    public Generator(int capacity, int maxExtract, int generation)
    {
        this(capacity, maxExtract, generation, 0);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        energyGeneration = compound.getInteger("EnergyGeneration");
        energyTicks = compound.getInteger("EnergyTicks");
        working = compound.getInteger("Working");

        energy.readFromNBT(compound);
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("EnergyGeneration", energyGeneration);
        compound.setInteger("EnergyTicks", energyTicks);
        compound.setInteger("Working", working);

        energy.writeToNBT(compound);
    }

    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            boolean should = false;

            if (energyTicks > 0) {
                working++;

                if (energyTicks == working) {
                    should = true;
                    working = 0;
                }
            }

            if (should) {
                boolean flag = (energy.getMaxEnergyStored(null) > energy.getEnergyStored(null)) && energyGeneration > 0;

                if (flag) {
                    if (canGenerate()) {
                        energy.increaseEnergyStored(energyGeneration);

                        sync();
                    }
                }
            }
        }
    }

    public abstract boolean canGenerate();

    /* Everything below is just a proxy for the interfaces */

    /* ENERGY */

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        int r = energy.extractEnergy(from, maxExtract, simulate);

        if (r > 0) {
            if (simulate) {
                sync();
            }

            return r;
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
