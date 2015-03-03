package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class Adaption extends Knowledge
{

    public Adaption()
    {
        super("adaption", ResourceHelper.texture("knowledge/adaption.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

}
