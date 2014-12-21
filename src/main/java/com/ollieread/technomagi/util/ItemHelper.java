package com.ollieread.technomagi.util;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.item.ItemMobBrain;
import com.ollieread.technomagi.item.ItemNaniteContainer;
import com.ollieread.technomagi.item.ItemSampleVile;
import com.ollieread.technomagi.item.ItemTMNBT;

public class ItemHelper
{

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

    public static ItemStack construct(int count)
    {
        return new ItemStack(Blocks.blockConstruct, count);
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
