package com.ollieread.technomagi.ability;

import com.ollieread.technomagi.api.ability.AbilityActive;
import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityFireball extends AbilityActive
{

    public ActiveAbilityFireball(String name)
    {
        super(name);
    }

    @Override
    public boolean canUse(PlayerKnowledge charon, Event event)
    {
        return true;
    }

    @Override
    public boolean isAvailable(PlayerKnowledge charon)
    {
        return false;
    }

    @Override
    public boolean use(PlayerKnowledge charon, Event event)
    {
        return false;
    }

}
