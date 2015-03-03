package com.ollieread.technomagi.common.knowledge.energies;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.EnumPlantType;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.api.scan.ScanHandler;
import com.ollieread.technomagi.common.knowledge.Energies;
import com.ollieread.technomagi.util.BlockRepresentation;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.ItemStackRepresentation;
import com.ollieread.technomagi.util.ResourceHelper;

public class EnergyLife extends Knowledge
{

    public static Research exposedToPocket;
    public static Research etheriumExposed;
    public static Research witnessItemExposure;
    public static Research witnessEntityExposure;
    public static Research witnessAreaExposure;

    public static Research witnessWitchHeal;
    public static Research breedAnimals;
    public static Research witnessAnimalBirth;
    public static Research analyseEgg;
    public static Research analyseCrops;
    public static Research analyseSapling;
    public static Research scanCrops;
    public static Research scanSapling;
    public static Research useBonemeal;
    public static Research witnessTreeGrow;

    // public static Research witnessCropGrow;

    public EnergyLife()
    {
        super("energy_life", ResourceHelper.texture("knowledge/energy_life.png"), Energies.category.getName());

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

        witnessWitchHeal = TechnomagiApi.addResearch("witness_witch_heal", getName()).setProgress(5).setChance(5).setRepetition(3); // 15
        breedAnimals = TechnomagiApi.addResearch("bread_animals", getName()).setProgress(3).setChance(8).setRepetition(6); // 18
        witnessAnimalBirth = TechnomagiApi.addResearch("witness_animal_birth", getName()).setProgress(2).setChance(6).setRepetition(4); // 8
        analyseEgg = TechnomagiApi.addResearch("analyse_egg", getName()).setProgress(1).setChance(4).setRepetition(5); // 5
        analyseCrops = TechnomagiApi.addResearch("analyse_crops", getName()).setProgress(1).setChance(4).setRepetition(5); // 5
        analyseSapling = TechnomagiApi.addResearch("analyse_saplings", getName()).setProgress(1).setChance(4).setRepetition(5); // 5
        scanCrops = TechnomagiApi.addResearch("scan_crops", getName()).setProgress(2).setChance(10).setRepetition(3); // 6
        scanSapling = TechnomagiApi.addResearch("scan_sapling", getName()).setProgress(2).setChance(10).setRepetition(3); // 6
        useBonemeal = TechnomagiApi.addResearch("use_bonemean", getName()).setProgress(1).setChance(8).setRepetition(6); // 6;
        witnessTreeGrow = TechnomagiApi.addResearch("witness_tree_grow", getName()).setProgress(4).setChance(4).setRepetition(2); // 8
    }

    public void mappings()
    {
        TechnomagiApi.scan().addAnalysisMapping(ScanHandler.getItemStackRepresentation(ItemStackHelper.item("egg")), analyseEgg.getName());
        TechnomagiApi.scan().addAnalysisMapping(new ItemStackRepresentation.PlantRepresentation(EnumPlantType.Crop), analyseCrops.getName());
        TechnomagiApi.scan().addAnalysisMapping(ScanHandler.getItemStackRepresentation(ItemStackHelper.block("sapling", 1, -1)), analyseSapling.getName());

        TechnomagiApi.scan().addScanMapping(new BlockRepresentation.GrowableRepresentation(), scanCrops.getName());
        TechnomagiApi.scan().addScanMapping(ScanHandler.getBlockRepresentation(Blocks.sapling, -1), scanSapling.getName());
    }
}