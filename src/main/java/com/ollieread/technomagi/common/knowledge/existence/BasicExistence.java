package com.ollieread.technomagi.common.knowledge.existence;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Existence;
import com.ollieread.technomagi.util.ResourceHelper;

public class BasicExistence extends Knowledge
{

    public BasicExistence()
    {
        super("basic_existence", ResourceHelper.texture("knowledge/basic_existence.png"), Existence.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}