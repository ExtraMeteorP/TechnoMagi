package com.ollieread.technomagi.api.ability;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class AbilityPassive<E> implements IAbilityPassive<E>
{

    public ResourceLocation abilityIcon;
    public String abilityName;

    public AbilityPassive(String name)
    {
        abilityName = name;
        abilityIcon = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/abilities/" + name + ".png");
    }

    public AbilityPassive(String name, ResourceLocation icon)
    {
        abilityName = name;
        abilityIcon = icon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ResourceLocation getIcon()
    {
        return abilityIcon;
    }

    @Override
    public String getName()
    {
        return abilityName;
    }

    public String getLocalisedName()
    {
        return I18n.format("ability.passive." + this.getName());
    }

}
