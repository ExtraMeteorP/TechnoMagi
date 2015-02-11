package com.ollieread.technomagi.common.block.conduit.tile;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.lib.util.helpers.EnergyHelper;

import com.ollieread.technomagi.common.block.tile.TileBase;

public class TileConduitPower extends TileBase implements IEnergyReceiver
{

    protected EnergyStorage energy;

    public TileConduitPower()
    {
        energy = new EnergyStorage(0);
    }

    public TileConduitPower(int capacity)
    {
        energy = new EnergyStorage(capacity);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return true;
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

    protected void distribute()
    {
        for (ForgeDirection direction : Arrays.asList(ForgeDirection.VALID_DIRECTIONS)) {
            TileEntity tile = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);

            if (tile instanceof IEnergyReceiver) {
                if (EnergyHelper.isAdjacentEnergyReceiverFromSide(tile, direction.getOpposite().ordinal())) {
                    IEnergyReceiver receiver = (IEnergyReceiver) tile;
                    int output = energy.extractEnergy(energy.getMaxExtract(), true);

                    if (output > 0) {
                        int input = ((IEnergyReceiver) tile).receiveEnergy(direction.getOpposite(), output, true);

                        if (input > 0) {
                            energy.extractEnergy(input, false);
                            ((IEnergyReceiver) tile).receiveEnergy(direction.getOpposite(), output, false);
                        }
                    }
                }
            }
        }
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

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        NBTTagCompound energyCompound = compound.getCompoundTag("Energy");
        energy = new EnergyStorage(energyCompound.getInteger("Capacity"));
        energy.readFromNBT(energyCompound);
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

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            distribute();
        }
    }

}
