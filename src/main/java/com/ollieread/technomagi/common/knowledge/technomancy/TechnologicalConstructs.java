package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class TechnologicalConstructs extends Knowledge
{

    public TechnologicalConstructs()
    {
        super("technological_constructs", ResourceHelper.texture("knowledge/technological_constructs.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public static void mappings()
    {

    }

}
