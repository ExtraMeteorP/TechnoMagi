package com.ollieread.technomagi.common.knowledge.organics;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Organics;
import com.ollieread.technomagi.util.ResourceHelper;

public class BasicOrganics extends Knowledge
{

    public BasicOrganics()
    {
        super("basic_organics", ResourceHelper.texture("knowledge/basic_organics.png"), Organics.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}