package com.ollieread.technomagi.common.block.electromagnetic;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket.EnergyType;
import com.ollieread.technomagi.common.block.BlockBaseContainer;
import com.ollieread.technomagi.common.block.electromagnetic.tile.TileElectromagnetic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockElectromagnetic extends BlockBaseContainer
{

    public BlockElectromagnetic(String name)
    {
        super(name, Material.glass);

        this.setCreativeTab(null);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileElectromagnetic();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        return Blocks.air.getIcon(side, metadata);
    }

    @Override
    public int getLightOpacity(IBlockAccess world, int x, int y, int z)
    {
        TileElectromagnetic tile = (TileElectromagnetic) world.getTileEntity(x, y, z);

        if (tile != null && tile.getType() != null) {
            if (tile.getType().equals(EnergyType.LIGHT) && tile.isNegative()) {
                return 255;
            }
        }

        return 0;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        TileElectromagnetic tile = (TileElectromagnetic) world.getTileEntity(x, y, z);

        if (tile != null && tile.getType() != null) {
            if (tile.getType().equals(EnergyType.LIGHT) && tile.isNegative()) {
                return 0;
            }
        }

        return 15;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        if (Technomagi.debug) {
            return true;
        }

        return false;
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
    {
        return false;
    }

    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
    {
    }

    @Override
    public boolean canSilkHarvest()
    {
        return false;
    }

}
