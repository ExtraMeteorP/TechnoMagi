package com.ollieread.technomagi.common.block.machine.tile;

import java.util.Arrays;

import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileCultivatorElectric extends TileCultivator implements IEnergyHandler
{

    protected EnergyStorage energy;
    protected int perUsage = 5;

    public TileCultivatorElectric()
    {
        super();

        maxNanites = 200;
        maxSample = 200;
        maxCultivated = 400;
        consumeNanites = 2;
        consumeSample = 2;

        energy = new EnergyStorage(3200, 10, 0);
    }

    @Override
    public boolean canProcess()
    {
        return super.canProcess() && energy.getEnergyStored() >= perUsage;
    }

    @Override
    public void consume()
    {
        energy.modifyEnergyStored(-perUsage);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return from == null || Arrays.asList(getValidDirections()).contains(from);
    }

    public ForgeDirection[] getValidDirections()
    {
        return ForgeDirection.VALID_DIRECTIONS;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        if (canConnectEnergy(from) && maxReceive > 0) {
            int r = energy.receiveEnergy(maxReceive, simulate);

            return r;
        }

        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        if (canConnectEnergy(from) && maxExtract > 0) {
            int r = energy.extractEnergy(maxExtract, simulate);

            return r;
        }

        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return energy.getEnergyStored();
    }

    public int getEnergyStoredScaled(int scale)
    {
        return energy.getEnergyStored() * scale / energy.getMaxEnergyStored();
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

}
