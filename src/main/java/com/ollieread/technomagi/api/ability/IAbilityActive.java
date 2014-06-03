package com.ollieread.technomagi.api.ability;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.common.eventhandler.Event;

public interface IAbilityActive
{

    public String getName();

    public ResourceLocation getIcon();

    public boolean canUse(PlayerKnowledge charon, Event event);

    public boolean isAvailable(PlayerKnowledge charon);

    public void use(PlayerKnowledge charon, Event event);

}
