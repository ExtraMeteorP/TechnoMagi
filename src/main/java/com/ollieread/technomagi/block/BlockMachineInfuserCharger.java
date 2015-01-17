package com.ollieread.technomagi.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.block.abstracts.BlockBasicContainer;
import com.ollieread.technomagi.tileentity.TileEntityMachineInfuserCharger;

public class BlockMachineInfuserCharger extends BlockBasicContainer
{

    public BlockMachineInfuserCharger(String name)
    {
        super(Material.iron, name);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        setBlockTextureName("construct");
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityMachineInfuserCharger();
    }

}
