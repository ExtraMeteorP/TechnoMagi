package com.ollieread.technomagi.research;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.event.ResearchEventKnowledgeChicken;
import com.ollieread.technomagi.research.event.ResearchEventKnowledgeCow;
import com.ollieread.technomagi.research.event.ResearchEventKnowledgePig;
import com.ollieread.technomagi.research.event.ResearchEventKnowledgeSheep;

public class KnowledgePassive extends Knowledge
{

    public static IResearchEvent pig;
    public static IResearchEvent sheep;
    public static IResearchEvent cow;
    public static IResearchEvent chicken;

    public KnowledgePassive(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        pig = new ResearchEventKnowledgePig("knowledgePig", this.getName(), 25);
        sheep = new ResearchEventKnowledgeSheep("knowledgeSheep", this.getName(), 25);
        cow = new ResearchEventKnowledgeCow("knowledgeCow", this.getName(), 25);
        chicken = new ResearchEventKnowledgeChicken("knowledgeChicken", this.getName(), 25);
    }

}
