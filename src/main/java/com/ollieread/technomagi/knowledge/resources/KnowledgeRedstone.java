package com.ollieread.technomagi.knowledge.resources;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.ennds.research.ShapelessAnalysisRecipe;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchAnalysis;
import com.ollieread.technomagi.research.ResearchMining;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgeRedstone extends Knowledge
{

    public static IResearchMining ore;
    public static IResearchAnalysis dust;
    public static IResearchAnalysis water;

    public KnowledgeRedstone(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_RESOURCES);

        ore = new ResearchMining("ore", getName(), 30, ItemHelper.block("redstone_ore"), 1, 16, new String[] {});
        dust = new ResearchAnalysis("dust", getName(), 30, new ShapelessAnalysisRecipe(ItemHelper.item("redstone")), 1, 20, new String[] {});
        water = new ResearchAnalysis("water", getName(), 40, new ShapelessAnalysisRecipe(ItemHelper.item("redstone"), ItemHelper.item("water_bucket")), 1, 20, new String[] {});
    }
}
