package com.ollieread.technomagi.common.recipes;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.crafting.IElectromagneticActionItem;
import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket.EnergyType;

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
    public ItemStack getInput(EnergyType type, boolean negative)
    {
        if (type.equals(EnergyType.HEAT) && !negative) {
            return input;
        }

        return null;
    }

    @Override
    public ItemStack getOutput(EnergyType type, boolean negative)
    {
        if (type.equals(EnergyType.HEAT) && !negative) {
            return output;
        }

        return null;
    }

    @Override
    public int getDuration(EnergyType type, boolean negative)
    {
        return 0;
    }

    @Override
    public int getCost(EnergyType type, boolean negative)
    {
        if (type.equals(EnergyType.HEAT) && !negative) {
            return 20;
        }

        return 0;
    }

    @Override
    public boolean perform(EnergyType type, boolean negative, World world, EntityItem item, int x, int y, int z)
    {
        if (type.equals(EnergyType.HEAT) && !negative) {
            return true;
        }

        return false;
    }

}
