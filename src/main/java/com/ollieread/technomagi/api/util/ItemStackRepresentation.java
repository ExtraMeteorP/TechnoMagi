package com.ollieread.technomagi.api.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * This class represents items as keys for maps, gets around the annoying
 * ItemStack issue where it doesn't implement equals{}. This would represent a
 * block placed normally in the world, or an ItemStack representing a block.
 * 
 * @author ollieread
 *
 */
public class ItemStackRepresentation
{

    protected String modid;
    protected String item;
    protected int damage;

    public ItemStackRepresentation(ItemStack stack)
    {
        String dirtyName = null;

        if (stack.getItem() instanceof ItemBlock) {
            dirtyName = Block.blockRegistry.getNameForObject(Block.getBlockFromItem(stack.getItem()));
        } else {
            dirtyName = Item.itemRegistry.getNameForObject(item);
        }

        if (dirtyName != null) {
            String[] nameParts = dirtyName.split(":");

            this.modid = nameParts[0];
            this.item = nameParts[1];
            this.damage = stack.getItemDamage();
        }
    }

    public ItemStackRepresentation(String modid, String item, int damage)
    {
        this.modid = modid;
        this.item = item;
        this.damage = damage;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o != null) {
            if (o instanceof ItemStackRepresentation) {
                ItemStackRepresentation item = (ItemStackRepresentation) o;

                return item.modid.equals(this.modid) && item.item.equals(this.item) && item.damage == this.damage;
            } else if (o instanceof ItemStack) {
                ItemStack item = (ItemStack) o;
                String dirtyName = Item.itemRegistry.getNameForObject(item.getItem());

                if (dirtyName != null) {
                    String[] nameParts = dirtyName.split(":");

                    return nameParts[0].equals(this.modid) && nameParts[1].equals(this.item) && item.getItemDamage() == this.damage;
                }
            }
        }

        return false;
    }

}
