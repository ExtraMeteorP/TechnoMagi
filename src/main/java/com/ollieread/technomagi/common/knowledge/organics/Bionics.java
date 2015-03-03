package com.ollieread.technomagi.common.knowledge.organics;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Organics;
import com.ollieread.technomagi.util.ResourceHelper;

public class Bionics extends Knowledge
{

    public Bionics()
    {
        super("bionics", ResourceHelper.texture("knowledge/bionics.png"), Organics.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}