package com.ollieread.technomagi.knowledge;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeLifeIII extends Knowledge
{

    public static IResearchEvent killZombie;
    public static IResearchEvent killSkeleton;
    public static IResearchEvent killVillager;
    public static IResearchEvent killEnderman;
    public static IResearchEvent killPig;
    public static IResearchEvent killCreeper;
    public static IResearchEvent killCow;
    public static IResearchEvent killSheep;

    public KnowledgeLifeIII(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        killZombie = new ResearchEvent("deathKillZombie", getName(), 10, "killedZombie", false, 2, new String[] { "rebirth" });
        killSkeleton = new ResearchEvent("deathKillSkeleton", getName(), 10, "killedSkeleton", false, 2, new String[] { "rebirth" });
        killVillager = new ResearchEvent("deathKillVillager", getName(), 10, "killedVillager", false, 2, new String[] { "rebirth" });
        killEnderman = new ResearchEvent("deathKillEnderman", getName(), 10, "killedEnderman", false, 2, new String[] { "rebirth" });
        killPig = new ResearchEvent("deathKillPig", getName(), 10, "killedPig", false, 2, new String[] { "rebirth" });
        killCreeper = new ResearchEvent("deathKillCreeper", getName(), 10, "killedCreeper", false, 2, new String[] { "rebirth" });
        killCow = new ResearchEvent("deathKillCow", getName(), 10, "killedCow", false, 2, new String[] { "rebirth" });
        killSheep = new ResearchEvent("deathKillSheep", getName(), 10, "killedSheep", false, 2, new String[] { "rebirth" });
    }

}
