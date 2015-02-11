package com.ollieread.technomagi.common.block.processor.tile;

import cofh.api.energy.EnergyStorage;

public class TileResourceProcessorNanites extends TileResourceProcessorElectric
{

    public TileResourceProcessorNanites()
    {
        super();

        this.modifier = 0.5F;
        this.baseConsume = 3;
    }

    @Override
    protected void createEnergyStorage()
    {
        this.energy = new EnergyStorage(6400, 25, 0);
    }

}
