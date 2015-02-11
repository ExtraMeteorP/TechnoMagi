package com.ollieread.technomagi.api.util;

import net.minecraft.block.Block;
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
    public boolean equals(Object o)
    {
        if (o != null) {
            if (o instanceof BlockRepresentation) {
                BlockRepresentation block = (BlockRepresentation) o;

                return block.modid.equals(this.modid) && block.block.equals(this.block) && block.metadata == this.metadata;
            } else if (o instanceof ItemStack) {
                ItemStack item = (ItemStack) o;
                String dirtyName = Block.blockRegistry.getNameForObject(Block.getBlockFromItem(item.getItem()));

                if (dirtyName != null) {
                    String[] nameParts = dirtyName.split(":");

                    return nameParts[0].equals(this.modid) && nameParts[1].equals(this.block) && item.getItemDamage() == this.metadata;
                }
            }
        }

        return false;
    }

}
