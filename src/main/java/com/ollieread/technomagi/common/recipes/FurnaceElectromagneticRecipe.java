package com.ollieread.technomagi.common.recipes;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.electromagnetic.EnergyHandler;
import com.ollieread.technomagi.api.electromagnetic.IElectromagneticActionItem;
import com.ollieread.technomagi.api.electromagnetic.EnergyHandler.EnergyType;

public class FurnaceElectromagneticRecipe implements IElectromagneticActionItem
{

    protected ItemStack input;
    protected ItemStack output;

    public FurnaceElectromagneticRecipe(ItemStack input, ItemStack output)
    {
        this.input = input;
        this.output = output;
    }

    @Override
    public ItemStack getInput(EnergyHandler.EnergyType type, boolean negative)
    {
        if (type.equals(EnergyHandler.EnergyType.HEAT) && !negative) {
            return input;
        }

        return null;
    }

    @Override
    public ItemStack getOutput(EnergyHandler.EnergyType type, boolean negative)
    {
        if (type.equals(EnergyHandler.EnergyType.HEAT) && !negative) {
            return output;
        }

        return null;
    }

    @Override
    public int getDuration(EnergyHandler.EnergyType type, boolean negative)
    {
        return 0;
    }

    @Override
    public int getCost(EnergyHandler.EnergyType type, boolean negative)
    {
        if (type.equals(EnergyHandler.EnergyType.HEAT) && !negative) {
            return 20;
        }

        return 0;
    }

    @Override
    public boolean perform(EnergyHandler.EnergyType type, boolean negative, World world, EntityItem item, int x, int y, int z)
    {
        if (type.equals(EnergyHandler.EnergyType.HEAT) && !negative) {
            return true;
        }

        return false;
    }

}
