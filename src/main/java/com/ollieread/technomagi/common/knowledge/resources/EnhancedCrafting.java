package com.ollieread.technomagi.common.knowledge.resources;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Resources;
import com.ollieread.technomagi.util.ResourceHelper;

public class EnhancedCrafting extends Knowledge
{

    public EnhancedCrafting()
    {
        super("enhanced_crafting", ResourceHelper.texture("knowledge/enhanced_crafting.png"), Resources.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}