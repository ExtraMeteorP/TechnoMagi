package com.ollieread.technomagi.common.block.energy.tile;

import com.ollieread.technomagi.common.component.Inventory;

public class TileGeneratorEnhanced extends TileGeneratorBasic
{

    public TileGeneratorEnhanced()
    {
        super(4096, 50, 50);

        perTick = 5;
    }

    @Override
    public void createInventory()
    {
        inventory = new Inventory("enhanced_generator", 2);
    }

}
