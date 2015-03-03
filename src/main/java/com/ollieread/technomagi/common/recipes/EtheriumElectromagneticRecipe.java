package com.ollieread.technomagi.common.recipes;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.crafting.IElectromagneticActionItem;
import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket.EnergyType;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.util.ItemStackHelper;

public class EtheriumElectromagneticRecipe implements IElectromagneticActionItem
{

    @Override
    public ItemStack getInput(EnergyType type, boolean negative)
    {
        return ItemStackHelper.itemSubtype(Items.resource, "etherium", 1);
    }

    @Override
    public ItemStack getOutput(EnergyType type, boolean negative)
    {
        if (type.equals(EnergyType.HEAT)) {
            if (negative) {
                return ItemStackHelper.itemSubtype(Items.resource, "glacite", 1);
            } else {
                return ItemStackHelper.itemSubtype(Items.resource, "calidite", 1);
            }
        } else if (type.equals(EnergyType.LIGHT)) {
            return ItemStackHelper.itemSubtype(Items.resource, "relux", 1);
        } else if (type.equals(EnergyType.VOID)) {
            return ItemStackHelper.itemSubtype(Items.resource, "void", 1);
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
        return 10;
    }

    @Override
    public boolean perform(EnergyType type, boolean negative, World world, EntityItem item, int x, int y, int z)
    {
        if (!type.equals(EnergyType.LIFE)) {
            ItemStack input = item.getEntityItem();
            input.stackSize--;

            if (input.stackSize <= 0) {
                item.setDead();
            }

            return true;
        }

        return false;
    }

}
