package com.ollieread.technomagi.api.ability;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IAbilityPassive<E>
{

    public String getName();

    @SideOnly(Side.CLIENT)
    public ResourceLocation getIcon();

    public int getEvent();

    public boolean canUse(PlayerKnowledge charon);

    public boolean isAvailable(PlayerKnowledge charon);

    public void use(E event, PlayerKnowledge charon);

    public String getLocalisedName();

}
