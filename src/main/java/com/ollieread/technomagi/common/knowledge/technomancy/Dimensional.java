package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class Dimensional extends Knowledge
{

    public Dimensional()
    {
        super("dimensional", ResourceHelper.texture("knowledge/dimensional.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

}
