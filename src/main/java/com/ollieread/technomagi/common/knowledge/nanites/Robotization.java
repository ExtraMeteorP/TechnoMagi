package com.ollieread.technomagi.common.knowledge.nanites;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Nanites;
import com.ollieread.technomagi.util.ResourceHelper;

public class Robotization extends Knowledge
{

    public Robotization()
    {
        super("robotization", ResourceHelper.texture("knowledge/robotization.png"), Nanites.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}