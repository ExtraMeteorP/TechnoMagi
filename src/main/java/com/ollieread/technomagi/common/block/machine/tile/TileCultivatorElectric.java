package com.ollieread.technomagi.common.block.machine.tile;

import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

import com.ollieread.technomagi.client.gui.window.WindowCultivatorElectric;
import com.ollieread.technomagi.client.gui.window.abstracts.Window;
import com.ollieread.technomagi.common.block.machine.container.ContainerCultivatorElectric;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
        chance = 3;

        createEnergyStorage();
    }

    protected void createEnergyStorage()
    {
        this.energy = new EnergyStorage(3200, 10, 0);
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

    public void setEnergyStored(int energy)
    {
        this.energy.setEnergyStored(energy);
    }

    public void modifyEnergyStored(int energy)
    {
        this.energy.modifyEnergyStored(energy);
    }

    @Override
    public Container getContainer(EntityPlayer player)
    {
        return new ContainerCultivatorElectric(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Window getWindow(EntityPlayer player)
    {
        return new WindowCultivatorElectric((ContainerCultivatorElectric) getContainer(player));
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

}
