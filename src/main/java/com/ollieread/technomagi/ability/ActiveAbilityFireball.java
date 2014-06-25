package com.ollieread.technomagi.ability;

import com.ollieread.technomagi.api.ability.AbilityActive;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityFireball extends AbilityActive
{

    public ActiveAbilityFireball(String name)
    {
        super(name);
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, Event event)
    {
        return true;
    }

    @Override
    public boolean isAvailable(ExtendedPlayerKnowledge charon)
    {
        return false;
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event)
    {
        return false;
    }

}
