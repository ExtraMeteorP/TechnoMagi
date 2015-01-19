package com.ollieread.technomagi.knowledge.biology;

import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.IResearchAnalysisRecipe;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.ennds.research.ShapelessAnalysisRecipe;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchAnalysis;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.research.ResearchMining;
import com.ollieread.technomagi.util.ItemHelper;

/**
 * @author ollieread
 *
 */
public class KnowledgeBotany extends Knowledge
{

    public static IResearch pickingFlowers1;
    public static IResearch pickingFlowers2;
    public static IResearch pickingFlowers3;
    public static IResearch pickingFlowers4;
    public static IResearch pickingFlowers5;
    public static IResearch pickingFlowers6;
    public static IResearch pickingFlowers7;
    public static IResearch pickingFlowers8;
    public static IResearch pickingFlowers9;
    public static IResearch shearedVines;
    public static IResearch flowerAnalysis;
    public static IResearch vineAnalysis;
    public static IResearch saplingAnalysis;
    public static IResearch leafAnalysis;
    public static IResearch logAnalysis;

    public KnowledgeBotany(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);

        IResearchAnalysisRecipe flowerList = new ShapelessAnalysisRecipe(ItemHelper.block("red_flower", 1, 0), ItemHelper.block("red_flower", 1, 1), ItemHelper.block("red_flower", 1, 2), ItemHelper.block("red_flower", 1, 3), ItemHelper.block("red_flower", 1, 4), ItemHelper.block("red_flower", 1, 5), ItemHelper.block("red_flower", 1, 6), ItemHelper.block("red_flower", 1, 7), ItemHelper.block("red_flower", 1, 8));
        IResearchAnalysisRecipe vineList = new ShapelessAnalysisRecipe(ItemHelper.block("vine"));
        IResearchAnalysisRecipe saplingList = new ShapelessAnalysisRecipe(ItemHelper.block("sapling", 1, 0), ItemHelper.block("sapling", 1, 1), ItemHelper.block("sapling", 1, 2), ItemHelper.block("sapling", 1, 3));
        IResearchAnalysisRecipe leafList = new ShapelessAnalysisRecipe(ItemHelper.block("leaves", 1, 0), ItemHelper.block("leaves", 1, 1), ItemHelper.block("leaves", 1, 2), ItemHelper.block("leaves", 1, 3));
        IResearchAnalysisRecipe logList = new ShapelessAnalysisRecipe(ItemHelper.block("log", 1, 0), ItemHelper.block("log", 1, 1), ItemHelper.block("log", 1, 2), ItemHelper.block("log", 1, 3));

        pickingFlowers1 = new ResearchMining("pickingFlowers1", getName(), ItemHelper.block("red_flower", 1, 0)).setChance(3).setProgress(5).setRepeatition(1).register();
        pickingFlowers2 = new ResearchMining("pickingFlowers2", getName(), ItemHelper.block("red_flower", 1, 1)).setChance(3).setProgress(5).setRepeatition(1).register();
        pickingFlowers3 = new ResearchMining("pickingFlowers3", getName(), ItemHelper.block("red_flower", 1, 2)).setChance(3).setProgress(5).setRepeatition(1).register();
        pickingFlowers4 = new ResearchMining("pickingFlowers4", getName(), ItemHelper.block("red_flower", 1, 3)).setChance(3).setProgress(5).setRepeatition(1).register();
        pickingFlowers5 = new ResearchMining("pickingFlowers5", getName(), ItemHelper.block("red_flower", 1, 4)).setChance(3).setProgress(5).setRepeatition(1).register();
        pickingFlowers6 = new ResearchMining("pickingFlowers6", getName(), ItemHelper.block("red_flower", 1, 5)).setChance(3).setProgress(5).setRepeatition(1).register();
        pickingFlowers7 = new ResearchMining("pickingFlowers7", getName(), ItemHelper.block("red_flower", 1, 6)).setChance(3).setProgress(5).setRepeatition(1).register();
        pickingFlowers8 = new ResearchMining("pickingFlowers8", getName(), ItemHelper.block("red_flower", 1, 7)).setChance(3).setProgress(5).setRepeatition(1).register();
        pickingFlowers9 = new ResearchMining("pickingFlowers9", getName(), ItemHelper.block("red_flower", 1, 8)).setChance(3).setProgress(5).setRepeatition(1).register();
        shearedVines = new ResearchEvent("shearedVines", getName(), "shearedVines").setChance(3).setProgress(5).setRepeatition(1).register();

        flowerAnalysis = new ResearchAnalysis("flowerAnalysis", getName(), flowerList).setChance(3).setProgress(10).setRepeatition(1).register();
        vineAnalysis = new ResearchAnalysis("vineAnalysis", getName(), vineList).setChance(3).setProgress(10).setRepeatition(1).register();
        saplingAnalysis = new ResearchAnalysis("saplingAnalysis", getName(), saplingList).setChance(3).setProgress(10).setRepeatition(1).register();
        leafAnalysis = new ResearchAnalysis("leafAnalysis", getName(), leafList).setChance(3).setProgress(10).setRepeatition(1).register();
        logAnalysis = new ResearchAnalysis("logAnalysis", getName(), logList).setChance(3).setProgress(10).setRepeatition(1).register();
    }

    @Override
    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
