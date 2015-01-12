package com.ollieread.technomagi.knowledge.casting;

import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;

public class KnowledgeCasting extends Knowledge
{

    public KnowledgeCasting(String name)
    {
        super(name, Reference.MODID.toLowerCase(), new String[] {}, KnowledgeReference.CATEGORY_CASTING);
    }

    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }
}
