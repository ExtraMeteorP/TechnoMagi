package com.ollieread.technomagi.api.ability;

import net.minecraft.item.EnumAction;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.entity.PlayerTechnomagi;

/**
 * This is an example implementation of {@link IAbilityCast}, and should be used
 * instead of implementing yourself, if at all possible.
 *
 * @author ollieread
 *
 */
public abstract class AbilityCast implements IAbilityCast
{

    protected String name;
    protected ResourceLocation icon;

    public AbilityCast(String name, ResourceLocation icon)
    {
        this.name = name;
        this.icon = icon;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getName(int abilityMode)
    {
        return name;
    }

    @Override
    public ResourceLocation getIcon(int abilityMode)
    {
        return icon;
    }

    @Override
    public String getUnlocalisedName(int abilityMode)
    {
        return "ability.cast." + name;
    }

    @Override
    public int getMaxFocus(int abilityMode)
    {
        return 0;
    }

    @Override
    public int getCooldown(int abilityMode)
    {
        return 0;
    }

    @Override
    public int switchModes(PlayerTechnomagi technomage, int abilityMode)
    {
        return abilityMode;
    }

    @Override
    public EnumAction getAction(int abilityMode)
    {
        return EnumAction.bow;
    }

}
