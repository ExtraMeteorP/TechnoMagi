package com.ollieread.technomagi.common.block.processor.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileResourceProcessorElectric extends TileResourceProcessor implements IEnergyHandler
{

    protected EnergyStorage energy;
    protected int baseConsume = 5;

    public TileResourceProcessorElectric()
    {
        createEnergyStorage();

        this.modifier = 0.75F;
    }

    protected void createEnergyStorage()
    {
        this.energy = new EnergyStorage(3200, 10, 0);
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {

        }

        super.updateEntity();
    }

    @Override
    public void consumeEnergy()
    {
        energy.modifyEnergyStored(baseConsume);
    }

    @Override
    public boolean hasSufficientEnergy()
    {
        return energy.getEnergyStored() > baseConsume;
    }

    @Override
    public int getInputSlot()
    {
        return 0;
    }

    @Override
    public int getComponentSlot()
    {
        return 1;
    }

    @Override
    public int getOutputSlot()
    {
        return 2;
    }

    @Override
    public int getByProductSlot()
    {
        return 3;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagCompound energyCompound = new NBTTagCompound();
        this.energy.writeToNBT(energyCompound);
        compound.setTag("Energy", energyCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        createEnergyStorage();
        this.energy.readFromNBT(compound.getCompoundTag("Energy"));
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return from.equals(ForgeDirection.DOWN) || from.equals(this.getDirection().getOpposite());
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        return energy.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        return energy.extractEnergy(maxExtract, simulate);
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

}
