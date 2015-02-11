package com.ollieread.technomagi.api.crafting;

public class CraftingHandler
{

    protected final static ProcessorRecipes RECIPE_PROCESSOR = new ProcessorRecipes();

    public static ProcessorRecipes processor()
    {
        return RECIPE_PROCESSOR;
    }

}
