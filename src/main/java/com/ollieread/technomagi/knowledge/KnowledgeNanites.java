package com.ollieread.technomagi.knowledge;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeNanites extends Knowledge
{

    public static IResearchEvent progress;

    public KnowledgeNanites(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        progress = new ResearchEvent("progress", this.getName(), 5, "knowledgeProgress", true, 1, null);
    }
}
