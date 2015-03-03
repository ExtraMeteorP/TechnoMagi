package com.ollieread.technomagi.util;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.item.ItemStack;

/**
 * This class represents blocks as keys for maps, gets around the annoying
 * ItemStack issue where it doesn't implement equals(). This is specifically for
 * block actions such as break, place, change, etc.
 *
 * @author ollieread
 *
 */
public class BlockRepresentation
{

    protected String modid;
    protected String block;
    protected int metadata;

    public BlockRepresentation(Block block, int metadata)
    {
        String dirtyName = Block.blockRegistry.getNameForObject(block);

        if (dirtyName != null) {
            String[] nameParts = dirtyName.split(":");

            this.modid = nameParts[0];
            this.block = nameParts[1];
            this.metadata = metadata;
        }
    }

    public BlockRepresentation(String modid, String block, int metadata)
    {
        this.modid = modid;
        this.block = block;
        this.metadata = metadata;
    }

    @Override
    public int hashCode()
    {
        return 10;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o != null) {
            if (o instanceof BlockRepresentation) {
                BlockRepresentation block = (BlockRepresentation) o;
                if (this.block != null && block.block != null) {
                    boolean flag = this.metadata == -1 || block.metadata == this.metadata;
                    return block.modid.equals(this.modid) && block.block.equals(this.block) && flag;
                }
            } else if (o instanceof ItemStack) {
                ItemStack item = (ItemStack) o;

                if (item != null && item.getItem() != null) {
                    String dirtyName = Block.blockRegistry.getNameForObject(Block.getBlockFromItem(item.getItem()));
                    boolean flag = this.metadata == -1 || item.getItemDamage() == this.metadata;

                    if (dirtyName != null) {
                        String[] nameParts = dirtyName.split(":");

                        return nameParts[0].equals(this.modid) && nameParts[1].equals(this.block) && flag;
                    }
                }
            }
        }

        return false;
    }

    public static class GrowableRepresentation extends BlockRepresentation
    {

        public GrowableRepresentation()
        {
            super(null, -1);
        }

        @Override
        public boolean equals(Object o)
        {
            if (o != null) {
                Block block = null;

                if (o instanceof BlockRepresentation) {
                    BlockRepresentation blockr = (BlockRepresentation) o;
                    block = (Block) Block.blockRegistry.getObject(blockr.block);
                } else if (o instanceof ItemStack) {
                    ItemStack item = (ItemStack) o;
                    block = Block.getBlockFromItem(item.getItem());
                }

                return block != null && block instanceof IGrowable;
            }

            return false;
        }
    }

}
