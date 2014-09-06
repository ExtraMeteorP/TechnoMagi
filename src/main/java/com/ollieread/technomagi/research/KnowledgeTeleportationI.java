package com.ollieread.technomagi.research;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.event.ResearchUseEnderpearl;

public class KnowledgeTeleportationI extends Knowledge
{

    public static IResearchEvent eventUseEnderpearl;

    public KnowledgeTeleportationI(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        eventUseEnderpearl = new ResearchUseEnderpearl("useEnderpearl", getName(), 10);
    }

}
