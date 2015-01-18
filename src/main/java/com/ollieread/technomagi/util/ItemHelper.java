package com.ollieread.technomagi.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.block.BlockMachine;
import com.ollieread.technomagi.block.BlockResource;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.item.ItemMobBrain;
import com.ollieread.technomagi.item.ItemNaniteContainer;
import com.ollieread.technomagi.item.ItemSampleVile;
import com.ollieread.technomagi.item.ItemTMNBT;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemHelper
{

    // EnumHelper.addToolMaterial("ENERGY", 4, 2200, 10.0F, 4.0F, 28);
    public static ToolMaterial ENERGY;
    public static ToolMaterial ETHERIUM;
    public static ToolMaterial RELUX;

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

    public static ItemStack staff(boolean complete)
    {
        ItemStack stack = new ItemStack(Items.itemTechnomageStaff);
        ItemTMNBT.resetNBT(stack);

        if (complete) {
            stack.setItemDamage(1);
        }

        return stack;
    }

    public static ItemStack sample(Class entityClass, int count)
    {
        ItemStack stack = null;

        if (entityClass == null) {
            stack = new ItemStack(Items.itemSampleVile, count);
            ItemTMNBT.resetNBT(stack);
        } else {
            stack = new ItemStack(Items.itemSampleVile, count);
            ItemSampleVile.setEntity(stack, entityClass);
        }

        return stack;
    }

    public static ItemStack nanites(Class entityClass, int count)
    {
        ItemStack stack = null;

        if (entityClass == null) {
            stack = new ItemStack(Items.itemNaniteContainer, count);
            ItemTMNBT.resetNBT(stack);
        } else {
            stack = new ItemStack(Items.itemNaniteContainer, count);
            ItemNaniteContainer.setEntity(stack, entityClass);
        }

        return stack;
    }

    public static ItemStack machine(String name, int count)
    {
        for (int i = 0; i < BlockMachine.blockNames.length; i++) {
            if (BlockMachine.blockNames[i].equals(name)) {
                return new ItemStack(Blocks.blockMachine, count, i);
            }
        }

        return null;
    }

    public static ItemStack construct(int count)
    {
        return machine("construct", count);
    }

    public static ItemStack ore(String name)
    {
        return ore(name, 1);
    }

    public static ItemStack ore(String name, int count)
    {
        ItemStack stack = new ItemStack(Blocks.blockResource, count);
        String[] blockNames = BlockResource.blockNames;

        for (int i = 0; i < blockNames.length; i++) {
            if (blockNames[i].equals(name)) {
                stack.setItemDamage(i);
                break;
            }
        }

        return stack;
    }

    public static ItemStack resource(String name, int count)
    {
        ItemStack stack = new ItemStack(Items.itemResource, count);
        String[] itemNames = Items.itemResource.itemNames;

        for (int i = 0; i < itemNames.length; i++) {
            if (itemNames[i].equals(name)) {
                stack.setItemDamage(i);
                break;
            }
        }

        return stack;
    }

    public static ItemStack component(String name, int count)
    {
        ItemStack stack = new ItemStack(Items.itemComponent, count);
        String[] itemNames = Items.itemComponent.itemNames;

        for (int i = 0; i < itemNames.length; i++) {
            if (itemNames[i].equals(name)) {
                stack.setItemDamage(i);
                break;
            }
        }

        return stack;
    }

    public static ItemStack brain(String name)
    {
        Class entityClass = (Class) EntityList.stringToClassMapping.get(name);

        if (entityClass != null) {
            return brain(entityClass);
        }

        return null;
    }

    public static ItemStack brain(Class entityClass)
    {
        ItemStack stack = new ItemStack(Items.itemMobBrain, 1);
        ((ItemMobBrain) Items.itemMobBrain).setEntity(stack, entityClass);

        return stack;
    }

    public static ItemStack brain()
    {
        ItemStack stack = new ItemStack(Items.itemMobBrain, 1);

        return stack;
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
