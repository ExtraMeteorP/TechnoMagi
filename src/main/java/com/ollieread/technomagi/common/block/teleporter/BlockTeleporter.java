package com.ollieread.technomagi.common.block.teleporter;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterAnchor;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterBeacon;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterBouncer;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterDeflector;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterFixed;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterInterruptor;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterJumper;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterNetworked;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterRelocator;

public class BlockTeleporter extends BlockContainerSubtypes
{

    public BlockTeleporter(String name)
    {
        super(name, new String[] {}, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        if (metadata == 0) {
            return new TileTeleporterFixed();
        } else if (metadata == 1) {
            return new TileTeleporterJumper();
        } else if (metadata == 2) {
            return new TileTeleporterBouncer();
        } else if (metadata == 3) {
            return new TileTeleporterInterruptor();
        } else if (metadata == 4) {
            return new TileTeleporterDeflector();
        } else if (metadata == 5) {
            return new TileTeleporterRelocator();
        } else if (metadata == 6) {
            return new TileTeleporterBeacon();
        } else if (metadata == 7) {
            return new TileTeleporterAnchor();
        }

        return null;
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata)
    {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(x, y, z);

            if (tile != null && tile instanceof TileTeleporterNetworked) {
                TileTeleporterNetworked networked = (TileTeleporterNetworked) tile;
                networked.removeFromNetwork();
            }
        }
    }

    @Override
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
    {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(x, y, z);

            if (tile != null && tile instanceof TileTeleporterNetworked) {
                TileTeleporterNetworked networked = (TileTeleporterNetworked) tile;
                networked.removeFromNetwork();
            }
        }

        super.onBlockExploded(world, x, y, z, explosion);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
    {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(x, y, z);

            if (tile != null && tile instanceof TileTeleporterNetworked) {
                TileTeleporterNetworked networked = (TileTeleporterNetworked) tile;
                networked.removeFromNetwork();
            }
        }

        super.breakBlock(world, x, y, z, block, metadata);
    }

}
