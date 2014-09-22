package com.ollieread.technomagi.research;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.event.ResearchEventKnowledgeCreeper;
import com.ollieread.technomagi.research.event.ResearchEventKnowledgeEnderman;
import com.ollieread.technomagi.research.event.ResearchEventKnowledgeSkeleton;
import com.ollieread.technomagi.research.event.ResearchEventKnowledgeZombie;

public class KnowledgeAggressive extends Knowledge
{

    public static IResearchEvent creeper;
    public static IResearchEvent enderman;
    public static IResearchEvent skeleton;
    public static IResearchEvent zombie;

    public KnowledgeAggressive(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        creeper = new ResearchEventKnowledgeCreeper("knowledgeCreeper", this.getName(), 25);
        enderman = new ResearchEventKnowledgeEnderman("knowledgeEnderman", this.getName(), 25);
        skeleton = new ResearchEventKnowledgeSkeleton("knowledgeSkeleton", this.getName(), 25);
        zombie = new ResearchEventKnowledgeZombie("knowledgeZombie", this.getName(), 25);
    }

}
