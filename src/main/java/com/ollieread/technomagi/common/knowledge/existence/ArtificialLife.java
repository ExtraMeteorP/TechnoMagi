package com.ollieread.technomagi.common.knowledge.existence;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Existence;
import com.ollieread.technomagi.util.ResourceHelper;

public class ArtificialLife extends Knowledge
{

    public ArtificialLife()
    {
        super("artificial_life", ResourceHelper.texture("knowledge/artificial_life.png"), Existence.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}