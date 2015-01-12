package com.ollieread.technomagi.knowledge.casting;

import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;

public class KnowledgeNegation extends Knowledge
{

    public KnowledgeNegation(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_CASTING);
    }

    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
