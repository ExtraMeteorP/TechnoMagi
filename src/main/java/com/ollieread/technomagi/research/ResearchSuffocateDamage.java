package com.ollieread.technomagi.research;

import com.ollieread.technomagi.api.research.Research;
import com.ollieread.technomagi.api.research.ResearchEvents;
import com.ollieread.technomagi.player.PlayerKnowledge;

public class ResearchSuffocateDamage extends Research
{

    public ResearchSuffocateDamage(String name, String knowledge, int progress)
    {
        super(name, knowledge, progress);
    }

    @Override
    public int getEvent()
    {
        return ResearchEvents.EVENT_IN_WALL;
    }

    @Override
    public boolean isRepeating()
    {
        return false;
    }

    @Override
    public boolean canPerform(PlayerKnowledge charon)
    {
        return true;
    }

}
