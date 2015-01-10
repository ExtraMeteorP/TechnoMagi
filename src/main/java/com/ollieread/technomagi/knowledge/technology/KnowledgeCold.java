package com.ollieread.technomagi.knowledge.technology;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.research.ResearchMining;
import com.ollieread.technomagi.util.EventHelper;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgeCold extends Knowledge
{

    public static IResearchMining snow;
    public static IResearchMining ice;
    public static IResearchEvent snowball;

    public KnowledgeCold(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_TECHNOLOGY);

        snow = new ResearchMining("snow", getName(), 20, ItemHelper.block("snow_layer"), 1, 4, new String[] {});
        ice = new ResearchMining("ice", getName(), 20, ItemHelper.block("ice"), 1, 4, new String[] {});
        snowball = new ResearchEvent("snowball", getName(), 4, EventHelper.itemUse(ItemHelper.item("snowball")), 5, 4, new String[] { snow.getName() });
    }
}
