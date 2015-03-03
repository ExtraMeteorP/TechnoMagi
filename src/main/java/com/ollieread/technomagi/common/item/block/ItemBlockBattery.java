package com.ollieread.technomagi.common.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import cofh.api.energy.IEnergyContainerItem;

import com.ollieread.technomagi.util.ItemNBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockBattery extends ItemBlockBase implements IEnergyContainerItem
{

    public ItemBlockBattery(Block block)
    {
        super(block);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return this.getEnergyStored(stack) < this.getMaxEnergyStored(stack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1 - (1D / this.getMaxEnergyStored(stack)) * this.getEnergyStored(stack);
    }

    public ItemBlockBattery setCapacity(ItemStack stack, int capacity)
    {
        ItemNBTHelper.setInteger(stack, "Capacity", capacity);

        return this;
    }

    public int getCapacity(ItemStack stack)
    {
        return ItemNBTHelper.getInteger(stack, "Capacity");
    }

    public ItemBlockBattery setMaxExtract(ItemStack stack, int maxExtract)
    {
        ItemNBTHelper.setInteger(stack, "MaxExtract", maxExtract);

        return this;
    }

    public int getMaxExtract(ItemStack stack)
    {
        return ItemNBTHelper.getInteger(stack, "MaxExtract");
    }

    public ItemBlockBattery setMaxReceive(ItemStack stack, int maxReceive)
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

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return stack.getItemDamage() == 0 ? EnumRarity.common : EnumRarity.uncommon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
    {
        int current = getEnergyStored(stack);
        int capacity = getMaxEnergyStored(stack);

        if (current == -1) {
            current = 0;

            if (stack.getItemDamage() == 0) {
                capacity = 6400;
            } else {
                capacity = 12800;
            }
        }

        list.add(EnumChatFormatting.GREEN + "" + current + "/" + capacity);
    }

}
