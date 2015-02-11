package com.ollieread.technomagi.api.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemHelper
{

    public static void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack)
    {
        if (!world.isRemote) {
            float f = 0.7F;
            double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }

    public static boolean containsKey(Map map, ItemStack stack, boolean nbt)
    {
        for (Iterator<ItemStack> i = map.keySet().iterator(); i.hasNext();) {
            ItemStack key = i.next();

            if (nbt) {
                if (ItemStack.areItemStacksEqual(key, stack)) {
                    return true;
                }
            } else {
                if (key.isItemEqual(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean containsValue(Map map, ItemStack stack, boolean nbt)
    {
        for (Iterator<ItemStack> i = map.values().iterator(); i.hasNext();) {
            ItemStack key = i.next();

            if (nbt) {
                if (ItemStack.areItemStacksEqual(key, stack)) {
                    return true;
                }
            } else {
                if (key.isItemEqual(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean contains(List list, ItemStack stack, boolean nbt)
    {
        for (Iterator<ItemStack> i = list.iterator(); i.hasNext();) {
            ItemStack entry = i.next();

            if (nbt) {
                if (ItemStack.areItemStacksEqual(entry, stack)) {
                    return true;
                }
            } else {
                if (entry.isItemEqual(entry)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean matchesBlock(ItemStack stack, Block block)
    {
        if (stack != null && stack.getItem() != null) {
            Block block2 = Block.getBlockFromItem(stack.getItem());

            if (block2 != null && block2 == block) {
                return true;
            }
        }

        return false;
    }

    public static boolean matches(ItemStack stack1, ItemStack stack2)
    {
        return matches(stack1, stack2, true);
    }

    public static boolean matches(ItemStack stack1, ItemStack stack2, boolean nbt)
    {
        if (!nbt) {
            return stack1.isItemEqual(stack2);
        }

        return ItemStack.areItemStacksEqual(stack1, stack2);
    }

    public static ItemStack item(String name)
    {
        return item(name, 1, 0);
    }

    public static ItemStack item(String name, int count)
    {
        return item(name, count, 0);
    }

    public static ItemStack item(String name, int count, int damage)
    {
        Item item = (Item) Item.itemRegistry.getObject(name);

        if (item != null) {
            return new ItemStack(item, count, damage);
        }

        return null;
    }

    public static ItemStack block(String modid, String name)
    {
        return block(modid, name, 1, 0);
    }

    public static ItemStack block(String modid, String name, int meta)
    {
        return block(modid, name, 1, meta);
    }

    public static ItemStack block(String modid, String name, int count, int meta)
    {
        Block block = GameRegistry.findBlock(modid, name);

        if (block != null) {
            return new ItemStack(Item.getItemFromBlock(block), count, meta);
        }

        return null;
    }

    public static ItemStack block(String name)
    {
        return block(name, 1, 0);
    }

    public static ItemStack block(String name, int count)
    {
        return block(name, count, 0);
    }

    public static ItemStack block(String name, int count, int damage)
    {
        Item item = Item.getItemFromBlock(Block.getBlockFromName(name));

        if (item != null) {
            return new ItemStack(item, count, damage);
        }

        return null;
    }
}
