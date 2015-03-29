package com.ollieread.technomagi.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

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

}
