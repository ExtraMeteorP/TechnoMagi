package com.ollieread.technomagi.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChamberFiller extends Block
{

    public BlockChamberFiller(String name)
    {
        super(Material.iron);

        setBlockTextureName("invisible");
        setBlockName(name);
        setCreativeTab(null);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        int meta = world.getBlockMetadata(x, y, z);

        if (meta == 0) {
            Blocks.blockObservationChamber.onBlockActivated(world, x, y - 1, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
        } else if (meta == 1) {
            Blocks.blockObservationChamber.onBlockActivated(world, x, y - 2, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
        }

        return false;
    }

    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion)
    {
        int meta = world.getBlockMetadata(x, y, z);

        if (meta == 0) {
            Blocks.blockObservationChamber.onBlockDestroyedByExplosion(world, x, y - 1, z, explosion);
        } else if (meta == 1) {
            Blocks.blockObservationChamber.onBlockDestroyedByExplosion(world, x, y - 2, z, explosion);
        }
    }

    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int p_149664_5_)
    {
        int meta = world.getBlockMetadata(x, y, z);

        if (meta == 0) {
            Blocks.blockObservationChamber.onBlockDestroyedByPlayer(world, x, y - 1, z, p_149664_5_);
        } else if (meta == 1) {
            Blocks.blockObservationChamber.onBlockDestroyedByPlayer(world, x, y - 2, z, p_149664_5_);
        }
    }

    public void onBlockHarvested(World world, int x, int y, int z, int p_149681_5_, EntityPlayer player)
    {
        int meta = world.getBlockMetadata(x, y, z);

        if (meta == 0) {
            Blocks.blockObservationChamber.onBlockHarvested(world, x, y - 1, z, p_149681_5_, player);
        } else if (meta == 1) {
            Blocks.blockObservationChamber.onBlockHarvested(world, x, y - 2, z, p_149681_5_, player);
        }
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }

}
