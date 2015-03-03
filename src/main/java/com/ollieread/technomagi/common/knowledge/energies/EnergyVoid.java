package com.ollieread.technomagi.common.knowledge.energies;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.common.knowledge.Energies;
import com.ollieread.technomagi.util.ResourceHelper;

public class EnergyVoid extends Knowledge
{

    public static Research exposedToPocket;
    public static Research etheriumExposed;
    public static Research witnessItemExposure;
    public static Research witnessEntityExposure;
    public static Research witnessAreaExposure;

    public EnergyVoid()
    {
        super("energy_void", ResourceHelper.texture("knowledge/energy_void.png"), Energies.category.getName());

        // Add prerequisites
        // addPrerequisite(blah);

        // Register
        TechnomagiApi.addKnowledge(this);

        // Research
        exposedToPocket = TechnomagiApi.addResearch("exposed_to_pocket", getName()).setProgress(3).setChance(8).setRepetition(10); // 30
        etheriumExposed = TechnomagiApi.addResearch("etherium_exposed", getName()).setProgress(4).setChance(8).setRepetition(10); // 40
        witnessItemExposure = TechnomagiApi.addResearch("witness_item_exposure", getName()).setProgress(2).setChance(8).setRepetition(10); // 20
        witnessEntityExposure = TechnomagiApi.addResearch("witness_entity_exposure", getName()).setProgress(2).setChance(8).setRepetition(10); // 20
        witnessAreaExposure = TechnomagiApi.addResearch("witness_block_exposure", getName()).setProgress(2).setChance(8).setRepetition(10); // 20
    }

    public void mappings()
    {
    }

}