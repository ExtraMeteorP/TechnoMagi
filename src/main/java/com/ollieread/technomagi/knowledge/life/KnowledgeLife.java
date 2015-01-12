package com.ollieread.technomagi.knowledge.life;

import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;

public class KnowledgeLife extends Knowledge
{

    public KnowledgeLife(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_LIFE);
    }

    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
