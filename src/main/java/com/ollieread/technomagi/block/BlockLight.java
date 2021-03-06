package com.ollieread.technomagi.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.block.abstracts.BlockBasic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLight extends BlockBasic
{

    public BlockLight(String name)
    {
        super(Material.air, name);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return net.minecraft.init.Blocks.air.getIcon(p_149691_1_, p_149691_2_);
    }

    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }

    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }

    public int getLightOpacity()
    {
        return 0;
    }

    public int getLightValue()
    {
        return 15;
    }

    public int getRenderType()
    {
        return -1;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
    {
        return false;
    }

    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
    {
    }

    public boolean canSilkHarvest()
    {
        return false;
    }

}
