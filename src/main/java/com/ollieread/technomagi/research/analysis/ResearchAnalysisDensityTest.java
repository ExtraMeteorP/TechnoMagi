package com.ollieread.technomagi.research.analysis;

import java.util.List;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.ResearchAnalysis;
import com.ollieread.technomagi.common.Reference;

public class ResearchAnalysisDensityTest extends ResearchAnalysis implements IResearchAnalysis
{

    public ResearchAnalysisDensityTest(String name, String knowledge, int progress, int chance, List items)
    {
        super(name, knowledge, progress, chance, items, Reference.MODID.toLowerCase());
    }

    @Override
    public boolean isRepeating()
    {
        return true;
    }

    @Override
    public boolean canPerform(ExtendedPlayerKnowledge charon)
    {
        return true;
    }

}
