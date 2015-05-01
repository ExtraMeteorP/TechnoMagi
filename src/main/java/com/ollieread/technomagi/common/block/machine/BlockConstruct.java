package com.ollieread.technomagi.common.block.machine;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockBaseContainer;
import com.ollieread.technomagi.common.block.machine.tile.TileConstruct;

public class BlockConstruct extends BlockBaseContainer
{

    public BlockConstruct(String name)
    {
        super(name, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileConstruct();
    }

    @Override
    public void registerTiles()
    {

    }

}
