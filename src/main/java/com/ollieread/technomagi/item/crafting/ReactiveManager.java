package com.ollieread.technomagi.item.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.item.ItemStack;

public class ReactiveManager
{

    private static final ReactiveManager instance = new ReactiveManager();
    private Map<Integer, Map<ItemStack, ItemStack>> recipes;
    private Map<Integer, Map<ItemStack, Integer>> recipeTimes;

    protected ReactiveManager()
    {
        recipes = new HashMap<Integer, Map<ItemStack, ItemStack>>();
        recipes.put(0, new HashMap<ItemStack, ItemStack>());
        recipes.put(1, new HashMap<ItemStack, ItemStack>());

        recipeTimes = new HashMap<Integer, Map<ItemStack, Integer>>();
        recipeTimes.put(0, new HashMap<ItemStack, Integer>());
        recipeTimes.put(1, new HashMap<ItemStack, Integer>());
    }

    public static final ReactiveManager getInstance()
    {
        return instance;
    }

    public void addRecipe(ItemStack block, ItemStack result, Integer time, int type)
    {
        if (block != null && result != null && !recipes.get(type).containsKey(block)) {
            recipes.get(type).put(block, result);
            recipeTimes.get(type).put(block, time);
        }
    }

    public List findMatchingRecipe(ItemStack stack, int type)
    {
        if (stack != null) {
            for (Iterator<ItemStack> i = recipes.get(type).keySet().iterator(); i.hasNext();) {
                ItemStack block = i.next();

                if (block.isItemEqual(stack)) {
                    List ret = new ArrayList();

                    ret.add(recipes.get(type).get(block));
                    ret.add(recipeTimes.get(type).get(block));

                    return ret;
                } else {
                    Block focus = Block.getBlockFromItem(block.getItem());
                    Block recipe = Block.getBlockFromItem(stack.getItem());

                    if (focus != null && recipe != null) {
                        if (focus instanceof BlockLog && recipe instanceof BlockLog) {
                            List ret = new ArrayList();

                            ret.add(recipes.get(type).get(stack));
                            ret.add(recipeTimes.get(type).get(stack));

                            return ret;
                        }
                    }
                }
            }
        }

        return null;
    }

}
