package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class AdaptionEnvironment extends Knowledge
{

    public AdaptionEnvironment()
    {
        super("adaption_environment", ResourceHelper.texture("knowledge/adaption_environment.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

}
