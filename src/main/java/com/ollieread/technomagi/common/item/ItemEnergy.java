package com.ollieread.technomagi.common.item;

import net.minecraft.item.ItemStack;
import cofh.api.energy.IEnergyContainerItem;

import com.ollieread.technomagi.util.ItemNBTHelper;

public class ItemEnergy extends ItemBase implements IEnergyContainerItem
{

    public ItemEnergy(String name)
    {
        super(name);
    }

    public ItemEnergy setCapacity(ItemStack stack, int capacity)
    {
        ItemNBTHelper.setInteger(stack, "Capacity", capacity);

        return this;
    }

    public int getCapacity(ItemStack stack)
    {
        return ItemNBTHelper.getInteger(stack, "Capacity");
    }

    public ItemEnergy setMaxExtract(ItemStack stack, int maxExtract)
    {
        ItemNBTHelper.setInteger(stack, "MaxExtract", maxExtract);

        return this;
    }

    public int getMaxExtract(ItemStack stack)
    {
        return ItemNBTHelper.getInteger(stack, "MaxExtract");
    }

    public ItemEnergy setMaxReceive(ItemStack stack, int maxReceive)
    {
        ItemNBTHelper.setInteger(stack, "MaxReceive", maxReceive);

        return this;
    }

    public int getMaxReceive(ItemStack stack)
    {
        return ItemNBTHelper.getInteger(stack, "MaxReceive");
    }

    public void configure(ItemStack stack, int capacity, int maxExtract, int maxReceive)
    {
        this.setCapacity(stack, capacity).setMaxExtract(stack, maxExtract).setMaxReceive(stack, maxReceive);
    }

    /* IEnergyContainerItem */
    @Override
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate)
    {
        int capacity = getCapacity(stack);
        int currentMaxReceive = getMaxReceive(stack);
        int energy = ItemNBTHelper.getInteger(stack, "Energy");
        int energyReceived = Math.min(capacity - energy, Math.min(currentMaxReceive, maxReceive));

        if (!simulate) {
            energy += energyReceived;
            ItemNBTHelper.setInteger(stack, "Energy", energy);
        }

        return energyReceived;
    }

    @Override
    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate)
    {
        int currentMaxExtract = getMaxExtract(stack);
        int energy = ItemNBTHelper.getInteger(stack, "Energy");
        int energyExtracted = Math.min(energy, Math.min(currentMaxExtract, maxExtract));

        if (!simulate) {
            energy -= energyExtracted;
            ItemNBTHelper.setInteger(stack, "Energy", energy);
        }

        return energyExtracted;
    }

    public void setEnergyStored(ItemStack stack, int stored)
    {
        ItemNBTHelper.setInteger(stack, "Energy", stored);
    }

    @Override
    public int getEnergyStored(ItemStack stack)
    {
        return ItemNBTHelper.getInteger(stack, "Energy");
    }

    @Override
    public int getMaxEnergyStored(ItemStack stack)
    {
        return getCapacity(stack);
    }

}
