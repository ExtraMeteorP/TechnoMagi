package com.ollieread.technomagi.knowledge.life;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchAnalysisRecipe;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.ennds.research.ShapelessAnalysisRecipe;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchAnalysis;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgeFlora extends Knowledge
{

    public static IResearchAnalysis flowers;
    public static IResearchAnalysis vines;
    public static IResearchAnalysis saplings;
    public static IResearchAnalysis leaves;
    public static IResearchAnalysis logs;

    public KnowledgeFlora(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_LIFE);

        IResearchAnalysisRecipe flowerList = new ShapelessAnalysisRecipe(ItemHelper.block("red_flower", 1, 0), ItemHelper.block("red_flower", 1, 1), ItemHelper.block("red_flower", 1, 2), ItemHelper.block("red_flower", 1, 3), ItemHelper.block("red_flower", 1, 4), ItemHelper.block("red_flower", 1, 5), ItemHelper.block("red_flower", 1, 6), ItemHelper.block("red_flower", 1, 7), ItemHelper.block("red_flower", 1, 8));
        IResearchAnalysisRecipe vineList = new ShapelessAnalysisRecipe(ItemHelper.block("vine"));
        IResearchAnalysisRecipe saplingList = new ShapelessAnalysisRecipe(ItemHelper.block("sapling", 1, 0), ItemHelper.block("sapling", 1, 1), ItemHelper.block("sapling", 1, 2), ItemHelper.block("sapling", 1, 3));
        IResearchAnalysisRecipe leafList = new ShapelessAnalysisRecipe(ItemHelper.block("leaves", 1, 0), ItemHelper.block("leaves", 1, 1), ItemHelper.block("leaves", 1, 2), ItemHelper.block("leaves", 1, 3));
        IResearchAnalysisRecipe logList = new ShapelessAnalysisRecipe(ItemHelper.block("log", 1, 0), ItemHelper.block("log", 1, 1), ItemHelper.block("log", 1, 2), ItemHelper.block("log", 1, 3));

        flowers = new ResearchAnalysis("flowers", getName(), 20, flowerList, 1, 3);
        vines = new ResearchAnalysis("vines", getName(), 20, vineList, 1, 3);
        saplings = new ResearchAnalysis("saplings", getName(), 20, saplingList, 1, 8);
        leaves = new ResearchAnalysis("leaves", getName(), 20, leafList, 1, 8);
        logs = new ResearchAnalysis("logs", getName(), 20, logList, 1, 8);
    }

    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
