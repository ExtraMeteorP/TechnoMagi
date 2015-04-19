package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class RestorationLiving extends Knowledge
{

    public RestorationLiving()
    {
        super("restoration_living", ResourceHelper.texture("knowledge/restoration_living.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public static void mappings()
    {

    }

}
