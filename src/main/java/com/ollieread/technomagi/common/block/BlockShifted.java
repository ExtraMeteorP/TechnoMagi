package com.ollieread.technomagi.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockShifted extends BlockBaseContainer
{

    protected BlockShifted(String name)
    {
        super(name, Material.glass);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return null;
    }

}
