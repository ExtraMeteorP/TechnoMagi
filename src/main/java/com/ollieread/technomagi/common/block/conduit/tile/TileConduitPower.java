package com.ollieread.technomagi.common.block.conduit.tile;

import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;

import com.ollieread.technomagi.common.block.energy.tile.TileEnergy;

public class TileConduitPower extends TileEnergy
{

    protected EnergyStorage energy;

    public TileConduitPower()
    {
        super(0);
    }

    public TileConduitPower(int capacity)
    {
        super(capacity);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return true;
    }

}
