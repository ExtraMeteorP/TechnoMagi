package com.ollieread.technomagi.common.knowledge.resources;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Resources;
import com.ollieread.technomagi.util.ResourceHelper;

public class AmbientResourceConstruction extends Knowledge
{

    public AmbientResourceConstruction()
    {
        super("ambient_resource_construction", ResourceHelper.texture("knowledge/ambient_resource_construction.png"), Resources.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}