package com.ollieread.technomagi.common.knowledge.resources;

import net.minecraft.init.Blocks;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.common.knowledge.Resources;
import com.ollieread.technomagi.util.BlockHelper;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.ResourceHelper;

public class BetterResources extends Knowledge
{

    public static Research mineIronOre;
    public static Research mineCopperOre;

    public static Research scanIronOre;
    public static Research scanCopperOre;

    public static Research analyseIronOre;
    public static Research analyseCopperOre;

    public static Research smeltIronIngot;
    public static Research smeltCopperIngot;

    public static Research analyseIronIngot;
    public static Research analyseCopperIngot;

    public static Research scanIronBlock;
    public static Research scanCopperBlock;

    public static Research analyseIronBlock;
    public static Research analyseCopperBlock;

    public BetterResources()
    {
        super("better_resources", ResourceHelper.texture("knowledge/better_resources.png"), Resources.category.getName());

        // Add prerequisites
        // addPrerequisite(blah);

        // Register
        TechnomagiApi.addKnowledge(this);

        scanIronOre = TechnomagiApi.addResearch("scan_iron_ore", getName()).setProgress(2).setChance(4).setRepetition(3); // 6
        scanCopperOre = TechnomagiApi.addResearch("scan_copper_ore", getName()).setProgress(2).setChance(4).setRepetition(3); // 6

        mineIronOre = TechnomagiApi.addResearch("mine_iron_ore", getName()).setProgress(2).setChance(4).setRepetition(2); // 4
        mineCopperOre = TechnomagiApi.addResearch("mine_copper_ore", getName()).setProgress(2).setChance(4).setRepetition(2); // 4

        analyseIronOre = TechnomagiApi.addResearch("analyse_iron_ore", getName()).setProgress(2).setChance(4).setRepetition(5); // 10
        analyseCopperOre = TechnomagiApi.addResearch("analyse_copper_ore", getName()).setProgress(2).setChance(4).setRepetition(5); // 10

        smeltIronIngot = TechnomagiApi.addResearch("smelt_iron_ingot", getName()).setProgress(2).setChance(4).setRepetition(5); // 10
        smeltCopperIngot = TechnomagiApi.addResearch("smelt_copper_ingot", getName()).setProgress(2).setChance(4).setRepetition(5); // 10

        analyseIronIngot = TechnomagiApi.addResearch("analyse_iron_ingot", getName()).setProgress(2).setChance(4).setRepetition(5); // 10
        analyseCopperIngot = TechnomagiApi.addResearch("analyse_copper_ingot", getName()).setProgress(2).setChance(4).setRepetition(5); // 10

        scanIronBlock = TechnomagiApi.addResearch("scan_iron_block", getName()).setProgress(2).setChance(4).setRepetition(3); // 6
        scanCopperBlock = TechnomagiApi.addResearch("scan_copper_block", getName()).setProgress(2).setChance(4).setRepetition(3); // 6

        analyseIronBlock = TechnomagiApi.addResearch("analyse_iron_block", getName()).setProgress(2).setChance(4).setRepetition(5); // 10
        analyseCopperBlock = TechnomagiApi.addResearch("analyse_copper_block", getName()).setProgress(2).setChance(4).setRepetition(5); // 10
    }

    public void mappings()
    {
        TechnomagiApi.knowledge().mapMiningResearch(Blocks.iron_ore, 0, mineIronOre.getName());
        TechnomagiApi.knowledge().mapMiningResearch(com.ollieread.technomagi.common.init.Blocks.resource, 2, mineCopperOre.getName());

        TechnomagiApi.scan().addScanMapping(BlockHelper.getBlockRepresentation(Blocks.iron_ore, 0), scanIronOre.getName());
        TechnomagiApi.scan().addScanMapping(BlockHelper.getBlockRepresentation(com.ollieread.technomagi.common.init.Blocks.resource, 2), scanCopperOre.getName());

        TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.block("iron_ore")), analyseIronOre.getName());
        TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.blockSubtype(com.ollieread.technomagi.common.init.Blocks.resource, "copper_ore", 1)), analyseCopperOre.getName());

        TechnomagiApi.knowledge().mapSmeltingResearch(ItemStackHelper.item("iron_ingot"), smeltIronIngot.getName());
        TechnomagiApi.knowledge().mapSmeltingResearch(ItemStackHelper.itemSubtype(Items.resource, "copper_ingot", 1), smeltCopperIngot.getName());

        TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.item("iron_ingot")), analyseIronIngot.getName());
        TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.itemSubtype(Items.resource, "copper_ingot", 1)), analyseCopperIngot.getName());

        TechnomagiApi.scan().addScanMapping(BlockHelper.getBlockRepresentation(Blocks.iron_block, 0), scanIronBlock.getName());
        TechnomagiApi.scan().addScanMapping(BlockHelper.getBlockRepresentation(com.ollieread.technomagi.common.init.Blocks.resource, 4), scanCopperBlock.getName());

        TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.block("iron_block")), analyseIronBlock.getName());
        TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.blockSubtype(com.ollieread.technomagi.common.init.Blocks.resource, "copper_block", 1)), analyseCopperBlock.getName());
    }
}
