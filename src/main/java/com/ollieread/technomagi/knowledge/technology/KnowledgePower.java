package com.ollieread.technomagi.knowledge.technology;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchCrafting;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgePower extends Knowledge
{

    public static IResearchEvent redstone;
    public static IResearchEvent gold;
    public static IResearchCrafting redstoneTorch;
    public static IResearchCrafting redstoneRepeater;
    public static IResearchCrafting powerWater;
    public static IResearchCrafting chargeableGeloid;

    public KnowledgePower(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_TECHNOLOGY);

        redstone = new ResearchEvent("redstone", getName(), 10, EventHelper.knowledge(com.ollieread.technomagi.common.init.Knowledge.redstone), 1, 1, true, new String[] {});
        gold = new ResearchEvent("gold", getName(), 10, EventHelper.knowledge(com.ollieread.technomagi.common.init.Knowledge.gold), 1, 1, true, new String[] {});
        redstoneTorch = new ResearchCrafting("redstoneTorch", getName(), 20, ItemHelper.block("redstone_torch"), 1, 2, new String[] {});
        redstoneRepeater = new ResearchCrafting("redstoneRepeater", getName(), 20, ItemHelper.block("unpowered_repeater"), 1, 2, new String[] {});
        powerWater = new ResearchCrafting("powerWater", getName(), 20, ItemHelper.component("powerWater", 1), 1, 2, new String[] {});
        chargeableGeloid = new ResearchCrafting("chargeableGeloid", getName(), 20, ItemHelper.component("chargeableGeloid", 1), 1, 2, new String[] {});
    }
}
