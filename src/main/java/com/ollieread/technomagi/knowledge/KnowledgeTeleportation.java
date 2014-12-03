package com.ollieread.technomagi.knowledge;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeTeleportation extends Knowledge
{

    public static IResearchEvent useEnderpearl;
    public static IResearchEvent witnessEndermanTeleport;
    public static IResearchEvent changeDimension;
    public static IResearchEvent toNether;
    public static IResearchEvent toEnd;
    public static IResearchEvent toOverworld;

    public KnowledgeTeleportation(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        useEnderpearl = new ResearchEvent("useEnderpearl", getName(), 25, "useEnderpearl", false, 2, null);
        witnessEndermanTeleport = new ResearchEvent("witnessEndermanTeleport", getName(), 25, "enderTeleportEnderman", false, 2, null);
        changeDimension = new ResearchEvent("changeDimension", getName(), 5, "playerChangedDimension", true, 2, null);
        toNether = new ResearchEvent("toNether", getName(), 10, "toNether", false, 2, null);
        toEnd = new ResearchEvent("toEnd", getName(), 10, "toEnd", false, 2, null);
        toOverworld = new ResearchEvent("toOverworld", getName(), 10, "toOverworld", false, 2, null);
    }

}
