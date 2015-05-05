package com.ollieread.technomagi.common.block.structure;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.block.BlockBaseContainer;
import com.ollieread.technomagi.common.block.structure.tile.TileTilted;
import com.ollieread.technomagi.common.misc.PotionTechnomagi;
import com.ollieread.technomagi.util.BlockHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTilted extends BlockBaseContainer
{

    public BlockTilted(String name)
    {
        super(name, Material.glass);

        setCreativeTab(null);
        setBlockUnbreakable();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileTilted();
    }

    @Override
    public void registerTiles()
    {
        BlockHelper.registerTileEntity(TileTilted.class, "tilted");
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPotionActive(PotionTechnomagi.phased)) {
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        if (Technomagi.proxy.getClientPlayer().isPotionActive(PotionTechnomagi.phased)) {
            return super.getSelectedBoundingBoxFromPool(world, x, y, z);
        }

        return AxisAlignedBB.getBoundingBox(x, y, z, x, y, z);
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public boolean canRenderInPass(int pass)
    {
        return pass == 1;
    }

    @Override
    public boolean canSilkHarvest()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    }

}
