package com.ollieread.technomagi.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.item.crafting.ConstructManager;
import com.ollieread.technomagi.item.crafting.CraftingManager;
import com.ollieread.technomagi.item.crafting.ReactiveManager;

public class RecipeHelper
{

    public static void addShapedRecipe(String knowledge, ItemStack output, Object... items)
    {
        CraftingManager.getInstance().addRecipe(knowledge, output, items);
    }

    public static void addShapelessRecipe(String knowledge, ItemStack output, Object... items)
    {
        CraftingManager.getInstance().addShapelessRecipe(knowledge, output, items);
    }

    public static void addConstructRecipe(Block block, ItemStack[] stacks)
    {
        ConstructManager.getInstance().addRecipe(block, stacks);
    }

    public static void addExothermicRecipe(ItemStack block, ItemStack result, Integer time)
    {
        ReactiveManager.getInstance().addRecipe(block, result, time, 0);
    }

    public static void addEndothermicRecipe(ItemStack block, ItemStack result, Integer time)
    {
        ReactiveManager.getInstance().addRecipe(block, result, time, 1);
    }

}
