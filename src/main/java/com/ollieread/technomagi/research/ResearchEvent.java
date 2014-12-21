package com.ollieread.technomagi.research;

import com.ollieread.ennds.common.Reference;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Research;
import com.ollieread.ennds.research.ResearchRegistry;

public class ResearchEvent extends Research implements IResearchEvent
{

    protected String event;
    protected int repeatition;
    protected int chance;
    protected boolean instant;

    public ResearchEvent(String name, String knowledge, int progress, String event, int repeatition, int chance, String[] requirements)
    {
        this(name, knowledge, progress, event, repeatition, chance, false, requirements);
    }

    public ResearchEvent(String name, String knowledge, int progress, String event, int repeatition, int chance, boolean instant, String[] requirements)
    {
        this(name, knowledge, progress, Reference.MODID.toLowerCase(), event, repeatition, chance, instant, requirements);
    }

    public ResearchEvent(String name, String knowledge, int progress, String Modid, String event, int repeatition, int chance, boolean instant, String[] requirements)
    {
        super(name, knowledge, progress, Modid, requirements);

        this.event = event;
        this.repeatition = repeatition;
        this.chance = chance;
        this.instant = instant;

        ResearchRegistry.registerResearch(this);
    }

    @Override
    public boolean isRepeating()
    {
        return repeatition > 1;
    }

    @Override
    public int getMaxRepeatition()
    {
        return repeatition;
    }

    @Override
    public int getChance()
    {
        return chance;
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
