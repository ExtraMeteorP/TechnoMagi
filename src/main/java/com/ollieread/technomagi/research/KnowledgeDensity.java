package com.ollieread.technomagi.research;

import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.mining.ResearchDensityHarvestObsidian;

public class KnowledgeDensity extends Knowledge
{

    public static IResearchMining densityObsidian;

    public KnowledgeDensity(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        densityObsidian = new ResearchDensityHarvestObsidian("densityObsidian", getName(), 25);
    }

}
