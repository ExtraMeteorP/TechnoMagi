package com.ollieread.technomagi.common.block.scanner;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.scanner.tile.TileScanner;

public class BlockScanner extends BlockContainerSubtypes
{

    public BlockScanner(String name)
    {
        super(name, new String[] { "position", "area", "analysis" }, Material.rock);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        TileScanner scanner = new TileScanner();
        scanner.setMode(metadata);

        return scanner;
    }

}