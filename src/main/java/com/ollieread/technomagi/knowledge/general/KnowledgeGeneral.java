package com.ollieread.technomagi.knowledge.general;

import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;

public class KnowledgeGeneral extends Knowledge
{

    public KnowledgeGeneral(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_GENERAL);
    }

    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }
}
