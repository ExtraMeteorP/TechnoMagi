package com.ollieread.technomagi.common.knowledge.resources;

import net.minecraft.init.Blocks;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.api.scan.ScanHandler;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.common.knowledge.Resources;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.ResourceHelper;

public class AdvancedResources extends Knowledge
{

    public static Research scanGoldOre;
    public static Research scanDiamondOre;
    public static Research scanObsidian;
    public static Research scanAluminiumOre;

    public static Research mineGoldOre;
    public static Research mineDiamondOre;
    public static Research mineObsidian;
    public static Research mineAluminiumOre;

    public static Research analyseGoldOre;
    public static Research analyseDiamond;
    public static Research analyseObsidian;
    public static Research analyseAluminiumOre;

    public static Research smeltGoldIngot;
    public static Research smeltAluminiumIngot;

    public static Research analyseGoldIngot;
    public static Research analyseAluminiumIngot;

    public static Research scanGoldBlock;
    public static Research scanAluminiumBlock;

    public AdvancedResources()
    {
        super("advanced_resources", ResourceHelper.texture("knowledge/advanced_resources.png"), Resources.category.getName());

        // Add prerequisites
        // addPrerequisite(blah);

        // Register
        TechnomagiApi.addKnowledge(this);

        scanGoldOre = TechnomagiApi.addResearch("scan_gold_ore", getName()).setProgress(2).setChance(4).setRepetition(3); // 6
        scanDiamondOre = TechnomagiApi.addResearch("scan_diamond_ore", getName()).setProgress(2).setChance(4).setRepetition(3); // 6
        scanObsidian = TechnomagiApi.addResearch("scan_obsidian", getName()).setProgress(2).setChance(4).setRepetition(3); // 6
        scanAluminiumOre = TechnomagiApi.addResearch("scan_aluminium_ore", getName()).setProgress(2).setChance(4).setRepetition(3); // 6

        mineGoldOre = TechnomagiApi.addResearch("mine_gold_ore", getName()).setProgress(2).setChance(4).setRepetition(2); // 4
        mineDiamondOre = TechnomagiApi.addResearch("mine_diamond_ore", getName()).setProgress(2).setChance(4).setRepetition(2); // 4
        mineObsidian = TechnomagiApi.addResearch("mine_obsidian", getName()).setProgress(2).setChance(4).setRepetition(2); // 4
        mineAluminiumOre = TechnomagiApi.addResearch("mine_aluminium_ore", getName()).setProgress(2).setChance(4).setRepetition(2); // 4

        analyseGoldOre = TechnomagiApi.addResearch("analyse_gold_ore", getName()).setProgress(2).setChance(4).setRepetition(5); // 10
        analyseDiamond = TechnomagiApi.addResearch("analyse_diamond", getName()).setProgress(2).setChance(4).setRepetition(4); // 8
        analyseObsidian = TechnomagiApi.addResearch("analyse_obsidian", getName()).setProgress(2).setChance(4).setRepetition(4); // 8
        analyseAluminiumOre = TechnomagiApi.addResearch("analyse_aluminium_ore", getName()).setProgress(2).setChance(4).setRepetition(5); // 10

        smeltGoldIngot = TechnomagiApi.addResearch("smelt_gold_ingot", getName()).setProgress(2).setChance(4).setRepetition(5); // 10
        smeltAluminiumIngot = TechnomagiApi.addResearch("smelt_aluminium_ingot", getName()).setProgress(2).setChance(4).setRepetition(5); // 10

        analyseGoldIngot = TechnomagiApi.addResearch("analyse_gold_ingot", getName()).setProgress(2).setChance(4).setRepetition(5); // 10
        analyseAluminiumIngot = TechnomagiApi.addResearch("analyse_aluminium_ingot", getName()).setProgress(2).setChance(4).setRepetition(5); // 10

        scanGoldBlock = TechnomagiApi.addResearch("scan_gold_block", getName()).setProgress(2).setChance(4).setRepetition(3); // 6
        scanAluminiumBlock = TechnomagiApi.addResearch("scan_aluminium_block", getName()).setProgress(2).setChance(4).setRepetition(3); // 6
    }

    public void mappings()
    {
        TechnomagiApi.scan().addScanMapping(ScanHandler.getBlockRepresentation(Blocks.gold_ore, 0), scanGoldOre.getName());
        TechnomagiApi.scan().addScanMapping(ScanHandler.getBlockRepresentation(Blocks.diamond_ore, 0), scanDiamondOre.getName());
        TechnomagiApi.scan().addScanMapping(ScanHandler.getBlockRepresentation(Blocks.obsidian, 0), scanObsidian.getName());
        TechnomagiApi.scan().addScanMapping(ScanHandler.getBlockRepresentation(com.ollieread.technomagi.common.init.Blocks.resource, 3), scanAluminiumOre.getName());

        TechnomagiApi.knowledge().mapMiningResearch(Blocks.gold_ore, 0, mineGoldOre.getName());
        TechnomagiApi.knowledge().mapMiningResearch(Blocks.diamond_ore, 0, mineDiamondOre.getName());
        TechnomagiApi.knowledge().mapMiningResearch(Blocks.obsidian, 0, mineObsidian.getName());
        TechnomagiApi.knowledge().mapMiningResearch(com.ollieread.technomagi.common.init.Blocks.resource, 3, mineAluminiumOre.getName());

        TechnomagiApi.scan().addAnalysisMapping(ScanHandler.getItemStackRepresentation(ItemStackHelper.block("gold_ore")), analyseGoldOre.getName());
        TechnomagiApi.scan().addAnalysisMapping(ScanHandler.getItemStackRepresentation(ItemStackHelper.block("obsidian")), analyseObsidian.getName());
        TechnomagiApi.scan().addAnalysisMapping(ScanHandler.getItemStackRepresentation(ItemStackHelper.item("diamond")), analyseDiamond.getName());
        TechnomagiApi.scan().addAnalysisMapping(ScanHandler.getItemStackRepresentation(ItemStackHelper.blockSubtype(com.ollieread.technomagi.common.init.Blocks.resource, "aluminium_ore", 1)), analyseAluminiumOre.getName());

        TechnomagiApi.knowledge().mapSmeltingResearch(ItemStackHelper.item("gold_ingot"), smeltGoldIngot.getName());
        TechnomagiApi.knowledge().mapSmeltingResearch(ItemStackHelper.itemSubtype(Items.resource, "aluminium_ingot", 1), smeltAluminiumIngot.getName());

        TechnomagiApi.scan().addAnalysisMapping(ScanHandler.getItemStackRepresentation(ItemStackHelper.item("gold_ingot")), analyseGoldIngot.getName());
        TechnomagiApi.scan().addAnalysisMapping(ScanHandler.getItemStackRepresentation(ItemStackHelper.itemSubtype(com.ollieread.technomagi.common.init.Items.resource, "aluminium_ingot", 1)), analyseAluminiumIngot.getName());

        TechnomagiApi.scan().addScanMapping(ScanHandler.getBlockRepresentation(Blocks.gold_block, 0), scanGoldBlock.getName());
        TechnomagiApi.scan().addScanMapping(ScanHandler.getBlockRepresentation(com.ollieread.technomagi.common.init.Blocks.resource, 5), scanAluminiumBlock.getName());
    }

}
