package com.ollieread.technomagi.common.knowledge.energies;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.common.knowledge.Energies;
import com.ollieread.technomagi.util.ResourceHelper;

public class ElectromagneticPockets extends Knowledge
{

    public static Research exposedToPocket;
    public static Research exposedToHeatPocket;
    public static Research exposedToVoidPocket;
    public static Research exposedToLifePocket;
    public static Research exposedToLightPocket;

    public ElectromagneticPockets()
    {
        super("electromagnetic_pockets", ResourceHelper.texture("knowledge/electromagnetic_pockets.png"), Energies.category.getName());

        // Add prerequisites
        // addPrerequisite(blah);

        // Register
        TechnomagiApi.addKnowledge(this);

        // Research
        exposedToPocket = TechnomagiApi.addResearch("exposed_to_pocket", getName()).setProgress(10).setChance(14);
        exposedToHeatPocket = TechnomagiApi.addResearch("exposed_to_heat_pocket", getName()).setProgress(3).setChance(8).setRepetition(10).setParent(exposedToPocket.getName());
        exposedToVoidPocket = TechnomagiApi.addResearch("exposed_to_void_pocket", getName()).setProgress(3).setChance(8).setRepetition(10).setParent(exposedToPocket.getName());
        exposedToLifePocket = TechnomagiApi.addResearch("exposed_to_life_pocket", getName()).setProgress(3).setChance(8).setRepetition(10).setParent(exposedToPocket.getName());
        exposedToLightPocket = TechnomagiApi.addResearch("exposed_to_light_pocket", getName()).setProgress(3).setChance(8).setRepetition(10).setParent(exposedToPocket.getName());
    }

    public void mappings()
    {
    }

}