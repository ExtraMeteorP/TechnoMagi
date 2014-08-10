package com.ollieread.technomagi.research.event;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Research;
import com.ollieread.technomagi.common.Reference;

public class ResearchUseEnderpearl extends Research implements IResearchEvent
{

    public ResearchUseEnderpearl(String name, String knowledge, int progress)
    {
        super(name, knowledge, progress, Reference.MODID.toLowerCase());
    }

    @Override
    public boolean canPerform(ExtendedPlayerKnowledge arg0)
    {
        return true;
    }

    @Override
    public boolean isRepeating()
    {
        return false;
    }

    @Override
    public String getEvent()
    {
        return "enderTeleport";
    }

}
