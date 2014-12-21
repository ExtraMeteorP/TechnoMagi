package com.ollieread.technomagi.research;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchAnalysisRecipe;
import com.ollieread.technomagi.common.Reference;

public class ResearchAnalysis extends com.ollieread.ennds.research.ResearchAnalysis implements IResearchAnalysis
{

    protected int repeatition;

    public ResearchAnalysis(String name, String knowledge, int progress, IResearchAnalysisRecipe recipe, int repeatition, int chance)
    {
        this(name, knowledge, progress, recipe, repeatition, chance, new String[] {});

        this.repeatition = repeatition;
    }

    public ResearchAnalysis(String name, String knowledge, int progress, IResearchAnalysisRecipe recipe, int repeatition, int chance, String[] requirements)
    {
        super(name, knowledge, progress, chance, Reference.MODID.toLowerCase(), recipe, requirements);

        this.repeatition = repeatition;
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
    public boolean canPerform(ExtendedPlayerKnowledge charon)
    {
        return true;
    }

}
