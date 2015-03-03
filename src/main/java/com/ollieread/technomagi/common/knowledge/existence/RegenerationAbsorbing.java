package com.ollieread.technomagi.common.knowledge.existence;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Existence;
import com.ollieread.technomagi.util.ResourceHelper;

public class RegenerationAbsorbing extends Knowledge
{

    public RegenerationAbsorbing()
    {
        super("regeneration_absorbing", ResourceHelper.texture("knowledge/regeneration_absorbing.png"), Existence.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}