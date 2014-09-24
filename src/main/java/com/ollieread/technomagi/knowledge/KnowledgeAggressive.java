package com.ollieread.technomagi.knowledge;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeAggressive extends Knowledge
{

    public static IResearchEvent creeper;
    public static IResearchEvent enderman;
    public static IResearchEvent skeleton;
    public static IResearchEvent zombie;

    public KnowledgeAggressive(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        creeper = new ResearchEvent("knowledgeCreeper", getName(), 25, "knowledgeEntityCreeper", false, 1, null);
        enderman = new ResearchEvent("knowledgeEnderman", getName(), 25, "knowledgeEntityEnderman", false, 1, null);
        skeleton = new ResearchEvent("knowledgeSkeleton", getName(), 25, "knowledgeEntitySkeleton", false, 1, null);
        zombie = new ResearchEvent("knowledgeZombie", getName(), 25, "knowledgeEntityZombie", false, 1, null);
    }

}
