package com.ollieread.technomagi.common.knowledge.energies;

import net.minecraft.init.Blocks;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.common.knowledge.Energies;
import com.ollieread.technomagi.util.BlockHelper;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.ResourceHelper;

public class EnergyLight extends Knowledge
{

    public static Research exposedToPocket;
    public static Research etheriumExposed;
    public static Research witnessItemExposure;
    public static Research witnessEntityExposure;
    public static Research witnessAreaExposure;

    public static Research craftTorch;
    public static Research scanFlower;
    public static Research analyseFlower;
    // public static Research witnessCropGrow;
    public static Research witnessTreeGrow;
    public static Research scanFire;
    public static Research scanGlowstone;
    public static Research analyseGlowstoneDust;

    public EnergyLight()
    {
        super("energy_light", ResourceHelper.texture("knowledge/energy_light.png"), Energies.category.getName());

        // Add prerequisites
        // addPrerequisite(blah);

        // Register
        TechnomagiApi.addKnowledge(this);

        // Research
        exposedToPocket = TechnomagiApi.addResearch("exposed_to_pocket", getName()).setProgress(2).setChance(8).setRepetition(5); // 10
        etheriumExposed = TechnomagiApi.addResearch("etherium_exposed", getName()).setProgress(4).setChance(8).setRepetition(1); // 4
        witnessItemExposure = TechnomagiApi.addResearch("witness_item_exposure", getName()).setProgress(2).setChance(8).setRepetition(4); // 8
        witnessEntityExposure = TechnomagiApi.addResearch("witness_entity_exposure", getName()).setProgress(2).setChance(8).setRepetition(4); // 8
        witnessAreaExposure = TechnomagiApi.addResearch("witness_block_exposure", getName()).setProgress(2).setChance(8).setRepetition(4); // 8

        craftTorch = TechnomagiApi.addResearch("craft_torch", getName()).setProgress(3).setChance(10).setRepetition(4); // 12
        scanFlower = TechnomagiApi.addResearch("scan_flower", getName()).setProgress(5).setChance(4).setRepetition(2); // 10
        analyseFlower = TechnomagiApi.addResearch("analyse_flower", getName()).setProgress(4).setChance(4).setRepetition(2); // 8
        // witnessCropGrow = TechnomagiApi.addResearch("witness_crop_grow",
        // getName()).setProgress(4).setChance(4).setRepetition(2); // 8
        witnessTreeGrow = TechnomagiApi.addResearch("witness_tree_grow", getName()).setProgress(4).setChance(4).setRepetition(2); // 8
        scanFire = TechnomagiApi.addResearch("scan_fire", getName()).setProgress(6).setChance(2); // 5
        scanGlowstone = TechnomagiApi.addResearch("scan_glowstone", getName()).setProgress(5).setChance(2); // 10
        analyseGlowstoneDust = TechnomagiApi.addResearch("analyse_flower", getName()).setProgress(4).setChance(4).setRepetition(2); // 8
    }

    public void mappings()
    {
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("torch"), craftTorch.getName());

        TechnomagiApi.scan().addScanMapping(BlockHelper.getBlockRepresentation(Blocks.red_flower, -1), scanFlower.getName());
        TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.block("red_flower", 1, -1)), analyseFlower.getName());
        TechnomagiApi.scan().addScanMapping(BlockHelper.getBlockRepresentation(Blocks.fire, 0), scanFire.getName());
        TechnomagiApi.scan().addScanMapping(BlockHelper.getBlockRepresentation(Blocks.glowstone, 0), scanGlowstone.getName());
        TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.item("glowstone_dust", 1, -1)), analyseGlowstoneDust.getName());
    }

}