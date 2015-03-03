package com.ollieread.technomagi.common.recipes;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.crafting.IElectromagneticActionItem;
import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket.EnergyType;
import com.ollieread.technomagi.util.ItemStackHelper;

public class GunpowderElectromagneticRecipe implements IElectromagneticActionItem
{

    @Override
    public ItemStack getInput(EnergyType type, boolean negative)
    {
        return ItemStackHelper.item("gunpowder");
    }

    @Override
    public ItemStack getOutput(EnergyType type, boolean negative)
    {
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
        return 10;
    }

    @Override
    public boolean perform(EnergyType type, boolean negative, World world, EntityItem item, int x, int y, int z)
    {
        if (type.equals(EnergyType.HEAT) && !negative) {
            world.createExplosion(item, item.posX, item.posY, item.posZ, 1, true);

            return true;
        }

        return false;
    }

}
