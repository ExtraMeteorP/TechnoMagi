package com.ollieread.technomagi.knowledge.resources;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.ennds.research.ShapelessAnalysisRecipe;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchAnalysis;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.research.ResearchMining;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgeCoal extends Knowledge
{

    public static IResearchMining ore;
    public static IResearchAnalysis coal;
    public static IResearchEvent smelting;

    public KnowledgeCoal(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_RESOURCES);

        ore = new ResearchMining("ore", getName(), 25, ItemHelper.block("coal_ore"), 1, 16, new String[] {});
        coal = new ResearchAnalysis("coal", getName(), 25, new ShapelessAnalysisRecipe(ItemHelper.item("coal")), 1, 20, new String[] {});
        smelting = new ResearchEvent("smelting", getName(), 5, "smelting", 10, 8, new String[] {});
    }

    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }
}
