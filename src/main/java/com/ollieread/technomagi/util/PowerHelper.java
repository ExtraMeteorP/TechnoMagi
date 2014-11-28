package com.ollieread.technomagi.util;

import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import cofh.lib.util.helpers.EnergyHelper;

import com.ollieread.technomagi.tileentity.TileEntityGenerator;

public class PowerHelper
{

    public static void pushToAdjacent(TileEntityGenerator tile, ForgeDirection from)
    {
        if (EnergyHelper.isEnergyHandlerFromSide(tile, from) && EnergyHelper.isAdjacentEnergyHandlerFromSide(tile, from.ordinal())) {
            int output = ((IEnergyHandler) tile).extractEnergy(from, ((IEnergyHandler) tile).getMaxEnergyStored(from), true);
            int input = EnergyHelper.insertEnergyIntoAdjacentEnergyHandler(tile, from.ordinal(), output, false);
            ((IEnergyHandler) tile).extractEnergy(from, output, false);
            tile.sync();
        }
    }
}