package com.ollieread.technomagi.util;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockHelper
{

    /**
     * Get an instance of {@link BlockRepresentation} for the provided
     * ItemStack.
     *
     * @param stack
     * @return
     */
    public static BlockRepresentation getBlockRepresentation(ItemStack stack)
    {
        return new BlockRepresentation(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage());
    }

    /**
     * Get an instance of {@link BlockRepresentation} for the provided block and
     * metadata.
     *
     * @param block
     * @param metadata
     * @return
     */
    public static BlockRepresentation getBlockRepresentation(Block block, int metadata)
    {
        return new BlockRepresentation(block, metadata);
    }

    /**
     * Applies a bonemeal style effect to a block.
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static boolean applyBonemeal(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);

        if (block != null && block instanceof IGrowable) {
            IGrowable igrowable = (IGrowable) block;

            if (igrowable.func_149851_a(world, x, y, z, world.isRemote)) {
                if (!world.isRemote) {
                    if (igrowable.func_149852_a(world, world.rand, x, y, z)) {
                        igrowable.func_149853_b(world, world.rand, x, y, z);
                    }
                }

                return true;
            }
        }

        return false;
    }

}
