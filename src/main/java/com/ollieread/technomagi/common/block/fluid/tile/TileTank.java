package com.ollieread.technomagi.common.block.fluid.tile;

import net.minecraftforge.fluids.FluidContainerRegistry;

import com.ollieread.technomagi.common.block.tile.TileFluid;

public class TileTank extends TileFluid
{

    public TileTank()
    {
        super(0);
    }

    public TileTank(int metadata)
    {
        super(FluidContainerRegistry.BUCKET_VOLUME * (metadata == 0 ? 100 : 1000));
    }

}
