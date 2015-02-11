package com.ollieread.technomagi.api.scan;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ScanItemStack
{

    protected String modid;
    protected String name;
    protected int metadata;

    public ScanItemStack(String modid, String name)
    {
        this(modid, name, 0);
    }

    public ScanItemStack(String modid, String name, int metadata)
    {
        this.modid = modid;
        this.name = name;
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o != null) {
            if (o instanceof ScanItemStack) {
                ScanItemStack item = (ScanItemStack) o;

                return item.modid.equals(this.modid) && item.name.equals(this.name) && item.metadata == this.metadata;
            } else if (o instanceof ItemStack) {
                ItemStack item = (ItemStack) o;
                String dirtyName = null;

                if (item.getItem() instanceof ItemBlock) {
                    dirtyName = Block.blockRegistry.getNameForObject(Block.getBlockFromItem(item.getItem()));
                } else {
                    dirtyName = Item.itemRegistry.getNameForObject(item.getItem());
                }

                if (dirtyName != null) {
                    String[] nameParts = dirtyName.split(":");

                    return nameParts[0].equals(this.modid) && nameParts[1].equals(this.name) && item.getItemDamage() == this.metadata;
                }
            }
        }

        return false;
    }

}
