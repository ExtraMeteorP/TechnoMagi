package com.ollieread.technomagi.api.ability;

import net.minecraft.item.EnumAction;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.ability.AbilityHandler.AbilityType;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This interface is for abilities that require casting by a staff item. The
 * {@link AbilityCast} implementation of this should be used wherver possible.
 * 
 * @author ollieread
 *
 */
public interface IAbilityCast
{

    /**
     * The identifying name for the ability. This is used internally.
     * 
     * @return
     */
    public String getName();

    /**
     * The identifying name for the ability. This is used when displaying the
     * ability name upon selection.
     * 
     * @param abilityMode The mode that the ability is currently in
     * @return
     */
    public String getName(int abilityMode);

    /**
     * The icon for the ability.
     * 
     * @param abilityMode The mode that the ability is currently in
     * @return
     */
    @SideOnly(Side.CLIENT)
    public ResourceLocation getIcon(int abilityMode);

    /**
     * Whether or not the ability is available. This is used when knowledge is
     * unlocked, to see whether or not the ability appears in the casting menu.
     * 
     * @param technomage The technomage instance, knowledge, etc
     * @return
     */
    public boolean isAvailable(PlayerTechnomagi technomage);

    /**
     * The unlocalised name for the ability, used for localisation.
     * 
     * @param abilityMode The mode that the ability is currently in
     * @return
     */
    public String getUnlocalisedName(int abilityMode);

    /**
     * The maximum amount of time the ability can be focused for.
     * 
     * @return
     */
    public int getMaxFocus(int abilityMode);

    /**
     * Whether or not the ability can be used with the current action.
     * 
     * An example would be an ability that requires an entity as a target, this
     * would check the payload and if the target is a block, it'd return false.
     * 
     * @param technomage The technomage instance, knowledge, etc
     * @param payload The payload for the current cast
     * @param abilityMode The mode that the ability is currently in
     * @return
     */
    public boolean canUse(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode);

    /**
     * This will actually perform the ability.
     * 
     * @param technomage The technomage instance, knowledge, etc
     * @param payload The payload for the current cast
     * @param abilityMode The mode that the ability is currently in
     */
    public void use(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode);

    /**
     * A way to do something specific once the ability usage has stopped,
     * particles, clean up data, etc.
     * 
     * @param technomage The technomage instance, knowledge, etc
     * @param payload The payload for the current cast
     * @param staff The ItemStack of the currently used staff
     * @param abilityMode The mode that the ability is currently in
     */
    public void stoppedUsing(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode);

    /**
     * The cooldown time for the ability, tick representation where 20 ticks are
     * approximately one second.
     * 
     * @param abilityMode The mode that the ability is currently in
     * @return
     */
    public int getCooldown(int abilityMode);

    /**
     * This method should switch ability modes, returning the new one.
     * 
     * @param abilityMode The mode that the ability is currently in
     * @return
     */
    public int switchModes(int abilityMode);

    /**
     * The type of ability.
     * 
     * @see AbilityType
     * @param abilityMode The mode that the ability is currently in
     * @return
     */
    public AbilityType getType(int abilityMode);

    /**
     * The animation action for the casting.
     * 
     * @param abilityMode The mode that the ability is currently in
     * @return
     */
    public EnumAction getAction(int abilityMode);

}
