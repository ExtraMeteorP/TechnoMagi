package com.ollieread.technomagi.knowledge.cybermind;

import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;

public class KnowledgeNavigation extends Knowledge
{

    public KnowledgeNavigation(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_CYBERMIND);
    }
}