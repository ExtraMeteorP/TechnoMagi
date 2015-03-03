package com.ollieread.technomagi.common.knowledge.arcane;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Arcane;
import com.ollieread.technomagi.util.ResourceHelper;

public class Brewing extends Knowledge
{

    public Brewing()
    {
        super("brewing", ResourceHelper.texture("knowledge/brewing.png"), Arcane.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}