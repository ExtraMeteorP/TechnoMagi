package com.ollieread.technomagi.common.block.processor;

import net.minecraft.block.material.Material;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.processor.tile.TileResourceProcessorBasic;
import com.ollieread.technomagi.common.block.processor.tile.TileResourceProcessorElectric;
import com.ollieread.technomagi.common.block.processor.tile.TileResourceProcessorNanites;

public class BlockResourceProcessor extends BlockContainerSubtypes
{

    protected IInventory inventory;

    public BlockResourceProcessor(String name)
    {
        super(name, new String[] { "processor_basic", "processor_electric", "processor_nanites" }, Material.rock);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        if (metadata == 0) {
            return new TileResourceProcessorBasic();
        } else if (metadata == 1) {
            return new TileResourceProcessorElectric();
        } else if (metadata == 2) {
            return new TileResourceProcessorNanites();
        }

        return null;
    }
}
