package com.ollieread.technomagi.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.ollieread.technomagi.item.crafting.ConstructManager;
import com.ollieread.technomagi.item.crafting.CraftingManager;
import com.ollieread.technomagi.item.crafting.ReactiveManager;
import com.ollieread.technomagi.item.crafting.SeparatorRecipes;

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

    public static void addConstructRecipe(Block block, ItemStack[] stacks, String knowledge)
    {
        ConstructManager.getInstance().addRecipe(block, stacks, knowledge);
    }

    public static void addExothermicRecipe(ItemStack block, ItemStack result, Integer time)
    {
        ReactiveManager.getInstance().addRecipe(block, result, time, 0);
    }

    public static void addEndothermicRecipe(ItemStack block, ItemStack result, Integer time)
    {
        ReactiveManager.getInstance().addRecipe(block, result, time, 1);
    }

    public static void addSeparatorRecipe(ItemStack stack, ItemStack result, ItemStack extra, int chance)
    {
        SeparatorRecipes.getInstance().addRecipe(stack, result, extra, chance);
    }

    public static void addFurnaceRecipe(ItemStack stack, ItemStack result, float exp)
    {
        FurnaceRecipes.smelting().func_151394_a(stack, result, exp);
    }

}
