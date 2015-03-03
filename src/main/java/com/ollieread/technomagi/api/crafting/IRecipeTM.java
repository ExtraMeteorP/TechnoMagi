package com.ollieread.technomagi.api.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IRecipeTM
{

    public boolean canCraft(EntityPlayer player);

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    boolean matches(IInventory p_77569_1_, World p_77569_2_);

    /**
     * Returns an Item that is the result of this recipe
     */
    ItemStack getCraftingResult(IInventory p_77572_1_);

    /**
     * Returns the size of the recipe area
     */
    int getRecipeSize();

    ItemStack getRecipeOutput();

    String getKnowledge();

}
