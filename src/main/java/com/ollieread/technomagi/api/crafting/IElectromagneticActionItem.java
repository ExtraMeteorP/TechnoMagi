package com.ollieread.technomagi.api.crafting;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.electromagnetic.EnergyHandler;
import com.ollieread.technomagi.api.electromagnetic.EnergyHandler.EnergyType;

public interface IElectromagneticActionItem
{

    public ItemStack getInput(EnergyHandler.EnergyType type, boolean negative);

    public ItemStack getOutput(EnergyHandler.EnergyType type, boolean negative);

    public int getDuration(EnergyHandler.EnergyType type, boolean negative);

    public int getCost(EnergyHandler.EnergyType type, boolean negative);

    public boolean perform(EnergyHandler.EnergyType type, boolean negative, World world, EntityItem item, int x, int y, int z);

}
