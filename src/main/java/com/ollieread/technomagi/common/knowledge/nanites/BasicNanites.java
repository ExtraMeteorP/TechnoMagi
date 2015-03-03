package com.ollieread.technomagi.common.knowledge.nanites;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Nanites;
import com.ollieread.technomagi.util.ResourceHelper;

public class BasicNanites extends Knowledge
{

    public BasicNanites()
    {
        super("basic_nanites", ResourceHelper.texture("knowledge/basic_nanites.png"), Nanites.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}