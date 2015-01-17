package com.ollieread.technomagi.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.TileEntityFiller;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFiller extends Block
{

    public BlockFiller(String name)
    {
        super(Material.iron);

        setBlockName(name);
    }

    public TileEntity createTileEntity(World world, int metdata)
    {
        return new TileEntityFiller();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        float f = 0.0625F;
        return AxisAlignedBB.getBoundingBox((double) ((float) x + f), (double) y, (double) ((float) z + f), (double) ((float) (x + 1) - f), (double) ((float) (y + 1) - f), (double) ((float) (z + 1) - f));
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float playerX, float playerY, float playerZ)
    {
        try {
            TileEntityFiller tileEntity = (TileEntityFiller) world.getTileEntity(x, y, z);

            if (tileEntity != null) {
                return tileEntity.getParent().onBlockActivated(world, tileEntity.parentX, tileEntity.parentY, tileEntity.parentZ, player, facing, playerX, playerY, playerZ);
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        try {
            TileEntityFiller tileEntity = (TileEntityFiller) world.getTileEntity(x, y, z);

            if (tileEntity != null) {
                tileEntity.getParent().onEntityCollidedWithBlock(world, x, y, z, entity);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity)
    {
        try {
            TileEntityFiller tileEntity = (TileEntityFiller) world.getTileEntity(x, y, z);

            if (tileEntity != null) {
                tileEntity.getParent().onEntityWalking(world, x, y, z, entity);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        try {
            TileEntityFiller tileEntity = (TileEntityFiller) world.getTileEntity(x, y, z);

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
            TileEntityFiller tileEntity = (TileEntityFiller) world.getTileEntity(x, y, z);

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
