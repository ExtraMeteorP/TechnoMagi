package com.ollieread.technomagi.knowledge.cybermind;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchEvent;

public class KnowledgeNaniteManipulation extends Knowledge
{

    public static IResearchEvent casting;

    public KnowledgeNaniteManipulation(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_CYBERMIND);

        casting = new ResearchEvent("casting", getName(), 2, "castingFinish", 50, 2, new String[] {});
    }
}