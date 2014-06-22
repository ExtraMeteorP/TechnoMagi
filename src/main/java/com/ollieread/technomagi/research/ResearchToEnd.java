package com.ollieread.technomagi.research;

import com.ollieread.technomagi.api.research.Research;
import com.ollieread.technomagi.api.research.ResearchEvents;
import com.ollieread.technomagi.player.PlayerKnowledge;

public class ResearchToEnd extends Research
{

    public ResearchToEnd(String name, String knowledge, int progress)
    {
        super(name, knowledge, progress);
    }

    @Override
    public int getEvent()
    {
        return ResearchEvents.EVENT_TO_END;
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
