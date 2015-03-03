package com.ollieread.technomagi.common.knowledge.arcane;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Arcane;
import com.ollieread.technomagi.util.ResourceHelper;

public class BasicArcane extends Knowledge
{

    public BasicArcane()
    {
        super("basic_arcane", ResourceHelper.texture("knowledge/basic_arcane.png"), Arcane.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}