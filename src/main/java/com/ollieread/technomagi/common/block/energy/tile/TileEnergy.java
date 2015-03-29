package com.ollieread.technomagi.common.block.energy.tile;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;

import com.ollieread.technomagi.common.block.tile.TileBase;

public class TileEnergy extends TileBase implements IEnergyHandler
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

    protected void distribute()
    {
        for (ForgeDirection direction : Arrays.asList(getValidDirections())) {
            TileEntity tile = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);

            if (tile instanceof IEnergyReceiver) {
                int amount = Math.min(this.getMaxExtract(), this.getEnergyStored(null));
                int actualAmount = this.extractEnergy(direction, amount, true);
                this.extractEnergy(direction, ((IEnergyReceiver) tile).receiveEnergy(direction.getOpposite(), actualAmount, false), false);
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

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        NBTTagCompound energyCompound = compound.getCompoundTag("Energy");
        energy = new EnergyStorage(energyCompound.getInteger("Capacity"), energyCompound.getInteger("MaxExtract"), energyCompound.getInteger("MaxReceive"));
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
        energyCompound.setInteger("MaxExtract", energy.getMaxExtract());
        energyCompound.setInteger("MaxReceive", energy.getMaxReceive());
        compound.setTag("Energy", energyCompound);
    }
}
