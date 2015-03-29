package com.ollieread.technomagi.common.block.structure;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockBaseContainer;

public class BlockEnergy extends BlockBaseContainer
{

    public BlockEnergy(String name)
    {
        super(name, Material.glass);

        this.setBlockUnbreakable();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return null;
    }

}
