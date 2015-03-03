package com.ollieread.technomagi.client.gui.content.element;

import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.client.gui.component.ComponentRecipe.RecipeType;

public class ElementRecipe extends Element
{

    public RecipeType type;
    public ItemStack output;

    public ElementRecipe(String name)
    {
        super(name);
    }

    public ElementRecipe setType(RecipeType type)
    {
        this.type = type;
        return this;
    }

    public ElementRecipe setOutput(ItemStack output)
    {
        this.output = output;
        return this;
    }

}
