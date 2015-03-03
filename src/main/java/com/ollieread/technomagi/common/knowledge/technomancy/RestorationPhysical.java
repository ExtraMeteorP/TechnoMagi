package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class RestorationPhysical extends Knowledge
{

    public RestorationPhysical()
    {
        super("restoration_physical", ResourceHelper.texture("knowledge/restoration_physical.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

}
