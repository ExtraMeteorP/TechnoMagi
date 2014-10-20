package com.ollieread.technomagi.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.TileEntityHardlightGenerator;

public class BlockHardlightGenerator extends BlockOwnable
{

    public BlockHardlightGenerator(String name)
    {
        super(Material.iron, name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TileEntityHardlightGenerator();
    }

}
