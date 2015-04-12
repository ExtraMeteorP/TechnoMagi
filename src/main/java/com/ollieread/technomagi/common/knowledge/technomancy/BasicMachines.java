package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class BasicMachines extends Knowledge
{

    public BasicMachines()
    {
        super("basic_machines", ResourceHelper.texture("knowledge/basic_machines.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {

    }

}
