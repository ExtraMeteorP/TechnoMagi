package com.ollieread.technomagi.api.crafting;

import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.api.crafting.CraftingHandler.ProcessorRecipes.ProcessorType;
import com.ollieread.technomagi.util.ItemStackHelper;

public class OreDictProcessorRecipe extends ProcessorRecipe
{

    public OreDictProcessorRecipe(ItemStack input)
    {
        super(input);
    }

    @Override
    public boolean matches(ProcessorType type, ItemStack input)
    {
        return super.matches(type, input) || ItemStackHelper.matchesOreDict(getInput(type), input);
    }

}
