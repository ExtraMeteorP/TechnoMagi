package com.ollieread.technomagi.knowledge.research;

import java.util.List;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.technomagi.common.Reference;

public class ResearchAnalysis extends com.ollieread.ennds.research.ResearchAnalysis implements IResearchAnalysis
{

    protected boolean repeating;
    protected String[] requirements;

    public ResearchAnalysis(String name, String knowledge, int progress, List items, boolean repeating, int chance, String[] requirements)
    {
        this(name, knowledge, progress, Reference.MODID.toLowerCase(), items, repeating, chance, requirements);
    }

    public ResearchAnalysis(String name, String knowledge, int progress, String Modid, List items, boolean repeating, int chance, String[] requirements)
    {
        super(name, knowledge, progress, chance, items, Modid);

        this.items = items;
        this.repeating = repeating;
        this.requirements = requirements;
    }

    @Override
    public boolean isRepeating()
    {
        return repeating;
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
