package com.ollieread.technomagi.knowledge.cybermind;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;

public class KnowledgeBrainManipulation extends Knowledge
{

    public static IResearchEvent passive;
    public static IResearchEvent aggressive;

    public KnowledgeBrainManipulation(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_CYBERMIND);

        passive = new ResearchEvent("passive", getName(), 50, EventHelper.knowledge(com.ollieread.technomagi.common.init.Knowledge.passive), 1, 1, true, new String[] {});
        aggressive = new ResearchEvent("aggressive", getName(), 50, EventHelper.knowledge(com.ollieread.technomagi.common.init.Knowledge.aggressive), 1, 1, true, new String[] {});
    }

}
