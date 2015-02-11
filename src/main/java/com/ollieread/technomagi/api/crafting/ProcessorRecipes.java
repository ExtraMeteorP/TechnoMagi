package com.ollieread.technomagi.api.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class ProcessorRecipes
{

    public static enum ProcessorType {
        GRIND, SAW
    }

    protected List<IProcessorRecipe> recipes = new ArrayList<IProcessorRecipe>();

    public void addRecipe(IProcessorRecipe recipe)
    {
        this.recipes.add(recipe);
    }

    public IProcessorRecipe findMatchingRecipe(ProcessorType type, ItemStack input)
    {
        for (IProcessorRecipe recipe : recipes) {
            if (recipe.getInput(type).isItemEqual(input)) {
                return recipe;
            }
        }

        return null;
    }

}
