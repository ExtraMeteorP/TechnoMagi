package com.ollieread.technomagi.common.block.structure;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.structure.tile.TileStructureBridge;
import com.ollieread.technomagi.common.block.structure.tile.TileStructurePlatform;

public class BlockStructure extends BlockContainerSubtypes
{

    public BlockStructure(String name)
    {
        super(name, new String[] { "platform", "bridge" }, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        switch (metadata) {
            case 0:
                return new TileStructurePlatform();
            case 1:
                return new TileStructureBridge();
            default:
                return null;
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (!world.isRemote) {
            if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
                TileEntity tile = world.getTileEntity(x, y, z);

                if (tile instanceof TileStructurePlatform) {
                    ((TileStructurePlatform) tile).toggle();
                } else if (tile instanceof TileStructureBridge) {
                    ((TileStructureBridge) tile).toggle(true);
                }
            }
        }
    }

}
