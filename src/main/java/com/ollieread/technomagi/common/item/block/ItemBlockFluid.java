package com.ollieread.technomagi.common.item.block;

import net.minecraft.block.Block;

public class ItemBlockFluid extends ItemBlockBase
{

    public ItemBlockFluid(Block block)
    {
        super(block);
    }

    @Override
    public int getMetadata(int metadata)
    {
        return 8;
    }

}
