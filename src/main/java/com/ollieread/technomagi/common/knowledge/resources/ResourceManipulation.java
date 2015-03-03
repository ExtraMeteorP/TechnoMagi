package com.ollieread.technomagi.common.knowledge.resources;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Resources;
import com.ollieread.technomagi.util.ResourceHelper;

public class ResourceManipulation extends Knowledge
{

    public ResourceManipulation()
    {
        super("resource_manipulation", ResourceHelper.texture("knowledge/resource_manipulation.png"), Resources.category.getName());

        // Add prerequisites
        // addPrerequisite(blah);

        // Register
        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}
