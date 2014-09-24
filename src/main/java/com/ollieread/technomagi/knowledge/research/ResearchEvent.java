package com.ollieread.technomagi.knowledge.research;

import com.ollieread.ennds.common.Reference;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Research;

public class ResearchEvent extends Research implements IResearchEvent
{

    protected String event;
    protected boolean repeating;
    protected int chance;
    protected String[] requirements;

    public ResearchEvent(String name, String knowledge, int progress, String event, boolean repeating, int chance, String[] requirements)
    {
        this(name, knowledge, progress, Reference.MODID.toLowerCase(), event, repeating, chance, requirements);
    }

    public ResearchEvent(String name, String knowledge, int progress, String Modid, String event, boolean repeating, int chance, String[] requirements)
    {
        super(name, knowledge, progress, Modid);

        this.event = event;
        this.repeating = repeating;
        this.chance = chance;
        this.requirements = requirements;
    }

    @Override
    public boolean isRepeating()
    {
        return repeating;
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
        if (requirements != null && requirements.length > 0) {
            for (int i = 0; i < requirements.length; i++) {
                if (!charon.hasKnowledge(requirements[i])) {
                    return false;
                }
            }
        }

        return true;
    }

}
