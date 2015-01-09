package com.ollieread.technomagi.knowledge.casting;

import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;

public class KnowledgeAmplification extends Knowledge
{

    public KnowledgeAmplification(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_CASTING);
    }

}
