package com.ollieread.technomagi.api.crafting;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket.EnergyType;

public interface IElectromagneticActionItem
{

    public ItemStack getInput(EnergyType type, boolean negative);

    public ItemStack getOutput(EnergyType type, boolean negative);

    public int getDuration(EnergyType type, boolean negative);

    public int getCost(EnergyType type, boolean negative);

    public boolean perform(EnergyType type, boolean negative, World world, EntityItem item, int x, int y, int z);

}
