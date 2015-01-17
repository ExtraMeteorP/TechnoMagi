package com.ollieread.technomagi.common.init;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.item.crafting.RecipeManager;

public class Recipes
{

    public static void init()
    {
        TechnoMagi.info("Initiating & registering recipes");

        Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.smelting().getSmeltingList();

        for (Iterator<Entry<ItemStack, ItemStack>> i = smeltingList.entrySet().iterator(); i.hasNext();) {
            Entry<ItemStack, ItemStack> entry = i.next();

            if (Block.getBlockFromItem(entry.getKey().getItem()) != null) {
                RecipeManager.reactive.add(entry.getKey(), entry.getValue(), 200);
            }
        }
    }
}
