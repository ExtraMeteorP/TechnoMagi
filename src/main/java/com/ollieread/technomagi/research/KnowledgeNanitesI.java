package com.ollieread.technomagi.research;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.event.ResearchEventKnowledgeProgress;

public class KnowledgeNanitesI extends Knowledge
{

    public static IResearchEvent progress;

    public KnowledgeNanitesI(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        progress = new ResearchEventKnowledgeProgress("progress", this.getName(), 5);
    }
}
