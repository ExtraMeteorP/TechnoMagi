package com.ollieread.technomagi.common.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.crafting.IRecipe;

public class Recipes
{

    public static List<IRecipe> recipes;

    public static void init()
    {
        recipes = new ArrayList<IRecipe>();
    }
}
