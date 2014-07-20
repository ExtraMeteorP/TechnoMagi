package com.ollieread.technomagi.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.TileEntityEmptyFiller;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEmptyFiller extends Block
{

    public BlockEmptyFiller(String name)
    {
        super(Material.iron);

        setBlockName(name);
    }

    public TileEntity createTileEntity(World world, int metdata)
    {
        return new TileEntityEmptyFiller();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float playerX, float playerY, float playerZ)
    {
        try {
            TileEntityEmptyFiller tileEntity = (TileEntityEmptyFiller) world.getTileEntity(x, y, z);

            if (tileEntity != null) {
                return tileEntity.getParent().onBlockActivated(world, tileEntity.parentX, tileEntity.parentY, tileEntity.parentZ, player, facing, playerX, playerY, playerZ);
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        try {
            TileEntityEmptyFiller tileEntity = (TileEntityEmptyFiller) world.getTileEntity(x, y, z);

            if (tileEntity != null) {
                tileEntity.getParent().getPickBlock(target, world, tileEntity.parentX, tileEntity.parentY, tileEntity.parentZ);
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {
        try {
            TileEntityEmptyFiller tileEntity = (TileEntityEmptyFiller) world.getTileEntity(x, y, z);

            if (tileEntity != null) {
                return tileEntity.getParent().removedByPlayer(world, player, tileEntity.parentX, tileEntity.parentY, tileEntity.parentZ);
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public Item getItemDropped(int i, Random random, int j)
    {
        return null;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

}
