package com.ollieread.technomagi.knowledge.technology;

import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;

public class KnowledgePossession extends Knowledge
{

    public KnowledgePossession(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_TECHNOLOGY);
    }

    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }
}