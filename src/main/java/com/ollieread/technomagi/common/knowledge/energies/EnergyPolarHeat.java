package com.ollieread.technomagi.common.knowledge.energies;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Energies;
import com.ollieread.technomagi.util.ResourceHelper;

public class EnergyPolarHeat extends Knowledge
{

    public EnergyPolarHeat()
    {
        super("energy_polar_heat", ResourceHelper.texture("knowledge/energy_polar_heat.png"), Energies.category.getName());

        // Add prerequisites
        // addPrerequisite(blah);

        // Register
        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}