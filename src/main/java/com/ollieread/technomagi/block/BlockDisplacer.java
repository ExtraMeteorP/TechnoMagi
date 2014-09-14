package com.ollieread.technomagi.block;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.util.GenerationHelper;

public class BlockDisplacer extends BlockTM
{

    public BlockDisplacer(String name)
    {
        super(Material.iron, name);
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        List affectedBlocks = GenerationHelper.getSphere(Vec3.createVectorHelper(x, y, z), world, 6, 6, 6, true);

        Iterator iterator = affectedBlocks.iterator();

        if (iterator != null) {
            while (iterator.hasNext()) {
                ChunkPosition pos = (ChunkPosition) iterator.next();
                Block block = world.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);

                if (world.isAirBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ) || block == net.minecraft.init.Blocks.water || block == net.minecraft.init.Blocks.flowing_water) {
                    world.setBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, Blocks.blockDisplacedAir);
                }
            }
        }
    }
}
