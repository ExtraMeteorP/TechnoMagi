package com.ollieread.technomagi.api.crafting;

import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.api.crafting.CraftingHandler.ProcessorRecipes.ProcessorType;

public interface IProcessorRecipe
{

    public ItemStack getInput(ProcessorType type);

    public ItemStack getOutput(ProcessorType type);

    public ItemStack getByProduct(ProcessorType type);

    public int getDamage(ProcessorType type);

    public int getByProductChance(ProcessorType type);

    public boolean matches(ProcessorType type, ItemStack input);

    public int getMinTier(ProcessorType type);

}
