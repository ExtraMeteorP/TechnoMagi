package com.ollieread.technomagi.knowledge.resources;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.ennds.research.ShapelessAnalysisRecipe;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchAnalysis;
import com.ollieread.technomagi.research.ResearchCrafting;
import com.ollieread.technomagi.research.ResearchMining;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgeIron extends Knowledge
{

    public static IResearchMining ore;
    public static IResearchAnalysis ingot;
    public static IResearchCrafting smelting;

    public KnowledgeIron(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_RESOURCES);

        ore = new ResearchMining("ore", getName(), 25, ItemHelper.block("iron_ore"), 1, 16, new String[] {});
        ingot = new ResearchAnalysis("ingot", getName(), 25, new ShapelessAnalysisRecipe(ItemHelper.item("iron_ingot")), 1, 20, new String[] {});
        smelting = new ResearchCrafting("smelting", getName(), 5, ItemHelper.item("iron_ingot"), 10, 8, new String[] {});
    }
}
