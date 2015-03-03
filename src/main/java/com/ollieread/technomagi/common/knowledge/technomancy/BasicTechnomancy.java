package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class BasicTechnomancy extends Knowledge
{

    public BasicTechnomancy()
    {
        super("basic_technomancy", ResourceHelper.texture("knowledge/basic_technomancy.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

}
