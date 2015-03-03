package com.ollieread.technomagi.common.knowledge.existence;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Existence;
import com.ollieread.technomagi.util.ResourceHelper;

public class Regeneration extends Knowledge
{

    public Regeneration()
    {
        super("regeneration", ResourceHelper.texture("knowledge/regeneration.png"), Existence.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}