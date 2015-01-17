package com.ollieread.technomagi.research;

import com.ollieread.ennds.common.Reference;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Research;

public class ResearchEvent extends Research implements IResearchEvent
{

    protected String event;
    protected boolean instant;

    public ResearchEvent(String name, String knowledge, String event)
    {
        super(name, knowledge, Reference.MODID.toLowerCase());

        this.event = event;
    }

    @Override
    public String getEvent()
    {
        return event;
    }

    @Override
    public boolean canPerform(ExtendedPlayerKnowledge charon)
    {
        return true;
    }

    @Override
    public boolean isInstant()
    {
        return instant;
    }

}
