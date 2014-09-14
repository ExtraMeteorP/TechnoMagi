package com.ollieread.technomagi.ability.active;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityExothermic extends AbilityActive
{

    public ActiveAbilityExothermic(String name)
    {
        super(name, Reference.MODID.toLowerCase());
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, Event event)
    {
        return charon.hasKnowledge("exothermic");
    }

    @Override
    public boolean isAvailable(ExtendedPlayerKnowledge charon)
    {
        return charon.hasKnowledge("exothermic");
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event)
    {
        return false;
    }

    @Override
    public String[] getEnhancements()
    {
        return new String[] { "exoI" };
    }

}
