package com.ollieread.technomagi.common.block.energy.tile;

import net.minecraft.inventory.IInventory;

import com.ollieread.technomagi.common.block.tile.ISideFacing;
import com.ollieread.technomagi.common.block.tile.ITileGui;
import com.ollieread.technomagi.common.block.tile.ITileProcessor;

public abstract class TileGenerator extends TileEnergy implements IInventory, ITileProcessor, ISideFacing, ITileGui
{

    public TileGenerator(int capacity, int maxReceive, int maxExtract)
    {
        super(capacity, maxReceive, maxExtract);
    }

}
