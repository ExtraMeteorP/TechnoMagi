package com.ollieread.technomagi.knowledge.cybermind;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchEvent;

public class KnowledgeDataManipulation extends Knowledge
{

    public static IResearchEvent progress;

    public KnowledgeDataManipulation(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_CYBERMIND);

        progress = new ResearchEvent("research", getName(), 2, "knowledgeProgress", 50, 3, true, new String[] {});
    }

}
