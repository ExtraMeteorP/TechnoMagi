package com.ollieread.technomagi.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityDisplacedAir;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDisplacedAir extends BlockContainer
{

    public BlockDisplacedAir(String name)
    {
        super(Material.glass);

        setBlockName(name);
        setBlockTextureName(name);
        setLightOpacity(0);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileEntityDisplacedAir();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return net.minecraft.init.Blocks.air.getIcon(p_149691_1_, p_149691_2_);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        world.setBlockToAir(x, y, z);
    }

    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }

    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }

    public int getRenderType()
    {
        return -1;
    }

    public int getLightValue()
    {
        return 15;
    }

    public boolean canSilkHarvest()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
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

    public boolean validateSelf(World world, int x, int y, int z)
    {
        TileEntityDisplacedAir tile = (TileEntityDisplacedAir) world.getTileEntity(x, y, z);

        if (tile != null) {
            return tile.validateSelf();
        }

        return false;
    }

}
