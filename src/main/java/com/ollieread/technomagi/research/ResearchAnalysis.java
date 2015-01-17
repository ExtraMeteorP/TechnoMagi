package com.ollieread.technomagi.research;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchAnalysisRecipe;
import com.ollieread.technomagi.common.Reference;

public class ResearchAnalysis extends com.ollieread.ennds.research.ResearchAnalysis implements IResearchAnalysis
{

    public ResearchAnalysis(String name, String knowledge, IResearchAnalysisRecipe recipe)
    {
        super(name, knowledge, Reference.MODID.toLowerCase(), recipe);

        this.recipe = recipe;
    }

    @Override
    public boolean canPerform(ExtendedPlayerKnowledge charon)
    {
        return true;
    }

}
