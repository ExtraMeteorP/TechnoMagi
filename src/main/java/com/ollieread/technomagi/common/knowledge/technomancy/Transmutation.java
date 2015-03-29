package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class Transmutation extends Knowledge
{

    public Transmutation()
    {
        super("transmutation", ResourceHelper.texture("knowledge/transmutation.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

}