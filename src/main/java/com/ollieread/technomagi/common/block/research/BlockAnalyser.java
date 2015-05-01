package com.ollieread.technomagi.common.block.research;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockBaseContainer;
import com.ollieread.technomagi.common.block.research.tile.TileAnalyser;
import com.ollieread.technomagi.util.BlockHelper;

public class BlockAnalyser extends BlockBaseContainer
{

    public BlockAnalyser(String name)
    {
        super(name, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileAnalyser();
    }

    @Override
    public void registerTiles()
    {
        BlockHelper.registerTileEntity(TileAnalyser.class, "analyser");
    }

}
