package com.ollieread.technomagi.common.knowledge.energies;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Energies;
import com.ollieread.technomagi.util.ResourceHelper;

public class Imbuement extends Knowledge
{

    public Imbuement()
    {
        super("imbuement", ResourceHelper.texture("knowledge/imbuement.png"), Energies.category.getName());

        // Add prerequisites
        // addPrerequisite(blah);

        // Register
        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}