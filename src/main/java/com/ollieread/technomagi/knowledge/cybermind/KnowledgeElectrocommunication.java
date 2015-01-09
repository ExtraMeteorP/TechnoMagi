package com.ollieread.technomagi.knowledge.cybermind;

import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;

public class KnowledgeElectrocommunication extends Knowledge
{

    public KnowledgeElectrocommunication(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_CYBERMIND);
    }
}