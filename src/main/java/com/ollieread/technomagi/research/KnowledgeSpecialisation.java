package com.ollieread.technomagi.research;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.event.ResearchEventSpecialised;

public class KnowledgeSpecialisation extends Knowledge
{

    public static IResearchEvent specialised;

    public KnowledgeSpecialisation(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        specialised = new ResearchEventSpecialised("specialised", this.getName(), 100);
    }
}
