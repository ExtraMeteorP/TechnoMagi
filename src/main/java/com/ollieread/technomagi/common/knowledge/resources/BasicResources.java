package com.ollieread.technomagi.common.knowledge.resources;

import net.minecraft.init.Blocks;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.common.knowledge.Resources;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.ResourceHelper;

public class BasicResources extends Knowledge
{

    // public static Research scanLog;
    // public static Research scanStone;
    public static Research analyseLog;
    public static Research analyseStone;
    public static Research analyseCobblestone;
    public static Research mineLog;
    public static Research mineStone;
    public static Research craftPlanks;
    public static Research craftSticks;

    public BasicResources()
    {
        super("basic_resources", ResourceHelper.texture("knowledge/basic_resources.png"), Resources.category.getName());

        // Add prerequisites
        // addPrerequisite(blah);

        // Register
        TechnomagiApi.addKnowledge(this);

        // scanLog = TechnomagiApi.addResearch("scan_log",
        // getName()).setProgress(5).setChance(12).setRepetition(1);
        // scanStone = TechnomagiApi.addResearch("scan_stone",
        // getName()).setProgress(5).setChance(12).setRepetition(1);
        analyseLog = TechnomagiApi.addResearch("analyse_log", getName()).setProgress(3).setChance(10).setRepetition(4);
        // analyseLog.setParent(scanLog.getName());

        analyseStone = TechnomagiApi.addResearch("analyse_stone", getName()).setProgress(3).setChance(10).setRepetition(4);
        // analyseStone.setParent(scanStone.getName());

        analyseCobblestone = TechnomagiApi.addResearch("analyse_cobblestone", getName()).setProgress(3).setChance(10).setRepetition(4);
        // analyseCobblestone.setParent(scanStone.getName());

        mineLog = TechnomagiApi.addResearch("mine_log", getName()).setProgress(4).setChance(22).setRepetition(5);
        // mineLog.setParent(scanLog.getName());

        mineStone = TechnomagiApi.addResearch("mine_stone", getName()).setProgress(4).setChance(22).setRepetition(5);
        // mineStone.setParent(scanStone.getName());

        craftPlanks = TechnomagiApi.addResearch("craft_planks", getName()).setProgress(3).setChance(20).setRepetition(5);
        craftPlanks.setParent(mineLog.getName());

        craftSticks = TechnomagiApi.addResearch("craft_sticks", getName()).setProgress(3).setChance(20).setRepetition(5);
        craftSticks.setParent(craftPlanks.getName());
    }

    public void mappings()
    {
        TechnomagiApi.knowledge().mapMiningResearch(Blocks.log, -1, mineLog.getName());
        TechnomagiApi.knowledge().mapMiningResearch(Blocks.stone, 0, mineStone.getName());

        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("planks"), craftPlanks.getName());
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.item("stick"), craftSticks.getName());

        // TechnomagiApi.scan().addScanMapping(BlockHelper.getBlockRepresentation(Blocks.log,
        // -1), scanLog.getName());
        // TechnomagiApi.scan().addScanMapping(BlockHelper.getBlockRepresentation(Blocks.stone,
        // 0), scanStone.getName());

        // TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.block("log")),
        // scanLog.getName());
        // TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.block("stone")),
        // scanStone.getName());
        // TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.block("cobblestone")),
        // scanStone.getName());
    }

}
