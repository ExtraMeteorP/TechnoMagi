package com.ollieread.technomagi.api.crafting;

import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.api.crafting.CraftingHandler.ProcessorRecipes.ProcessorType;

/**
 * This interface is for processor components such as grinding heads and/or
 * saws.
 *
 * @author ollieread
 *
 */
public interface IProcessorComponent
{

    /**
     *
     * @param stack
     * @return
     */
    public ProcessorType getType(ItemStack stack);

    /**
     *
     * @param stack
     * @return
     */
    public int getMaxDamage(ItemStack stack);

    /**
     *
     * @param stack
     * @param damage
     */
    public void setCurrentDamage(ItemStack stack, int damage);

    /**
     *
     * @param stack
     * @return
     */
    public int getCurrentDamage(ItemStack stack);

    /**
     *
     * @param stack
     * @return
     */
    public int getDuplicationChance(ItemStack stack);

    /**
     *
     * @param stack
     * @return
     */
    public int getMaxDuration(ItemStack stack);

    /**
     *
     * @param stack
     * @return
     */
    public boolean isBroken(ItemStack stack);

    /**
     *
     * @param stack
     * @return
     */
    public int getTier(ItemStack stack);

}
