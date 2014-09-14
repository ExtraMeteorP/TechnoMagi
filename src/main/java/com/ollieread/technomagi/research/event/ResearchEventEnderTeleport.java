package com.ollieread.technomagi.research.event;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Research;
import com.ollieread.technomagi.common.Reference;

public class ResearchEventEnderTeleport extends Research implements IResearchEvent
{

    public ResearchEventEnderTeleport(String name, String knowledge, int progress)
    {
        super(name, knowledge, progress, Reference.MODID.toLowerCase());
    }

    @Override
    public boolean isRepeating()
    {
        return false;
    }

    @Override
    public boolean canPerform(ExtendedPlayerKnowledge charon)
    {
        return true;
    }

    @Override
    public String getEvent()
    {
        return "enderTeleport";
    }

    @Override
    public int getChance()
    {
        return 1;
    }

}
