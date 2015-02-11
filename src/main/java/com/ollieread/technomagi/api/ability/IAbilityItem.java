package com.ollieread.technomagi.api.ability;

import net.minecraft.item.ItemStack;

/**
 * This interface is for items that allow a player to cast.
 * 
 * @author ollieread
 *
 */
public interface IAbilityItem
{

    /**
     * Whether or not the item is locked to a single ability.
     * 
     * Some items are specifically created to perform a single ability, unlike
     * the staff, which can perform any from the pool available. If this returns
     * true, {@link IAbilityItem#getLockedAbility()} should return the ability.
     * 
     * @param stack The ItemStack containing the item
     * @return
     */
    public boolean isAbilityLocked(ItemStack stack);

    /**
     * The ability that the item is locked to. This is only used when
     * {@link IAbilityItem#isAbilityLocked()} returns true.
     * 
     * @param stack The ItemStack containing the item
     * @return
     */
    public IAbilityCast getLockedAbility(ItemStack stack);

    /**
     * If the item is locked to an ability, this will specify the mode.
     * 
     * 
     * @param stack The ItemStack containing the item
     * @return
     */
    public int getAbilityMode(ItemStack stack);

    /**
     * Whether or not the player can cast, this could be a power check.
     * 
     * @param stack The ItemStack containing the item
     * @return
     */
    public boolean canCast(ItemStack stack);

}
