package com.ollieread.technomagi.api.ability;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IAbilityPassive<E>
{

    public String getName();

    @SideOnly(Side.CLIENT)
    public ResourceLocation getIcon();

    public int getEvent();

    public boolean canUse(ExtendedPlayerKnowledge charon);

    public boolean isAvailable(ExtendedPlayerKnowledge charon);

    public void use(E event, ExtendedPlayerKnowledge charon);

    public String getLocalisedName();

}
