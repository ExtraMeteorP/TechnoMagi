package com.ollieread.technomagi.knowledge.technology;

import net.minecraft.init.Items;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.ennds.research.ShapedAnalysisRecipe;
import com.ollieread.ennds.research.ShapelessAnalysisRecipe;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchAnalysis;
import com.ollieread.technomagi.research.ResearchCrafting;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgeWarmth extends Knowledge
{

    public static IResearchEvent coal;
    public static IResearchEvent photoreactive;
    public static IResearchAnalysis flintAndSteelRecipe;
    public static IResearchCrafting flintAndSteelCrafting;
    public static IResearchAnalysis flintAndSteelAnalysis;
    public static IResearchAnalysis torchRecipe;
    public static IResearchCrafting torchCrafting;
    public static IResearchAnalysis torchAnalysis;

    public KnowledgeWarmth(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_TECHNOLOGY);

        coal = new ResearchEvent("coal", getName(), 15, EventHelper.knowledge(com.ollieread.technomagi.common.init.Knowledge.coal), 1, 1, true, new String[] {});
        photoreactive = new ResearchEvent("photoreactive", getName(), 15, EventHelper.knowledge(com.ollieread.technomagi.common.init.Knowledge.photoreactive), 1, 1, true, new String[] {});
        flintAndSteelRecipe = new ResearchAnalysis("flintAndSteelRecipe", getName(), 10, new ShapelessAnalysisRecipe(ItemHelper.item("iron_ingot"), ItemHelper.item("stick")), 1, 4, new String[] {});
        flintAndSteelCrafting = new ResearchCrafting("flintAndSteelCrafting", getName(), 10, ItemHelper.item("flint_and_steel"), 1, 4, new String[] {});
        flintAndSteelRecipe = new ResearchAnalysis("flintAndSteelAnalysis", getName(), 15, new ShapelessAnalysisRecipe(ItemHelper.item("flint_and_steel")), 1, 4, new String[] {});
        torchRecipe = new ResearchAnalysis("torchRecipe", getName(), 10, new ShapedAnalysisRecipe("X", "#", '#', Items.stick, 'X', Items.redstone), 1, 4, new String[] {});
        torchCrafting = new ResearchCrafting("torchCrafting", getName(), 10, ItemHelper.block("torch"), 4, 1, new String[] {});
        torchAnalysis = new ResearchAnalysis("torchAnalysis", getName(), 15, new ShapelessAnalysisRecipe(ItemHelper.block("torch")), 1, 4, new String[] {});
    }
}
