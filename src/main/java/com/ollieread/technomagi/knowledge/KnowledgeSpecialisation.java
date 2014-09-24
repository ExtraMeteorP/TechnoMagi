package com.ollieread.technomagi.knowledge;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeSpecialisation extends Knowledge
{

    public static IResearchEvent specialised;

    public KnowledgeSpecialisation(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        specialised = new ResearchEvent("specialised", this.getName(), 100, "specialisation", false, 1, null);
    }
}
