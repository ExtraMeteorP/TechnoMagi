package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class AdvancedMachines extends Knowledge
{

    public AdvancedMachines()
    {
        super("advanced_machines", ResourceHelper.texture("knowledge/advanced_machines.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

}
