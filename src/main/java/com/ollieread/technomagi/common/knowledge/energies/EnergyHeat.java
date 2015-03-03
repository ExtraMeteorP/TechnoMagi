package com.ollieread.technomagi.common.knowledge.energies;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.common.knowledge.Energies;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.ResourceHelper;

public class EnergyHeat extends Knowledge
{

    public static Research exposedToPocket;
    public static Research etheriumExposed;
    public static Research witnessItemExposure;
    public static Research witnessEntityExposure;
    public static Research witnessAreaExposure;

    public static Research onFire;
    public static Research inFire;
    public static Research inLava;
    public static Research craftTorch;
    public static Research itemBurnInLava;
    public static Research pickupLava;
    public static Research craftFlintAndSteel;
    public static Research smelting;

    public EnergyHeat()
    {
        super("energy_heat", ResourceHelper.texture("knowledge/energy_heat.png"), Energies.category.getName());

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

        onFire = TechnomagiApi.addResearch("on_fire", getName()).setProgress(4).setChance(3).setRepetition(5); // 10
        inFire = TechnomagiApi.addResearch("in_fire", getName()).setProgress(4).setChance(3).setRepetition(2); // 8
        inLava = TechnomagiApi.addResearch("in_lava", getName()).setProgress(4).setChance(8).setRepetition(3); // 12
        craftTorch = TechnomagiApi.addResearch("craft_torch", getName()).setProgress(3).setChance(10).setRepetition(4); // 12
        itemBurnInLava = TechnomagiApi.addResearch("item_burn_in_lava", getName()).setProgress(1).setChance(8).setRepetition(6); // 6
        pickupLava = TechnomagiApi.addResearch("pickup_lava", getName()).setProgress(4).setChance(8).setRepetition(2); // 8
        craftFlintAndSteel = TechnomagiApi.addResearch("craft_flint_and_steel", getName()).setProgress(5).setChance(3).setRepetition(1); // 5
        smelting = TechnomagiApi.addResearch("smelting", getName()).setProgress(1).setChance(5).setRepetition(7); // 7
    }

    public void mappings()
    {
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("torch"), craftTorch.getName());
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.item("flint_and_steel"), craftFlintAndSteel.getName());
    }

}