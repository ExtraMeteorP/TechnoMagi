package com.ollieread.technomagi.common.block.teleporter;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockBaseContainer;
import com.ollieread.technomagi.common.block.teleporter.tile.TileElevator;

public class BlockElevator extends BlockBaseContainer
{

    public BlockElevator(String name)
    {
        super(name, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileElevator();
    }

}
