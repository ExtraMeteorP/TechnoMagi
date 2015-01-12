package com.ollieread.technomagi.knowledge.cybermind;

import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;

public class KnowledgeCybermind extends Knowledge
{

    public KnowledgeCybermind(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_CYBERMIND);
    }

    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }
}
