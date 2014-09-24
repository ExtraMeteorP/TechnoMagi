package com.ollieread.technomagi.knowledge;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgePassive extends Knowledge
{

    public static IResearchEvent pig;
    public static IResearchEvent sheep;
    public static IResearchEvent cow;
    public static IResearchEvent chicken;

    public KnowledgePassive(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        pig = new ResearchEvent("knowledgePig", this.getName(), 25, "knowledgeEntityPig", false, 1, null);
        sheep = new ResearchEvent("knowledgeSheep", this.getName(), 25, "knowledgeEntitySheep", false, 1, null);
        cow = new ResearchEvent("knowledgeCow", this.getName(), 25, "knowledgeEntityCow", false, 1, null);
        chicken = new ResearchEvent("knowledgeChicken", this.getName(), 25, "knowledgeEntityChicken", false, 1, null);
    }

}
