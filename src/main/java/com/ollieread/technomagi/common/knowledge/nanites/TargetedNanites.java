package com.ollieread.technomagi.common.knowledge.nanites;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Nanites;
import com.ollieread.technomagi.util.ResourceHelper;

public class TargetedNanites extends Knowledge
{

    public TargetedNanites()
    {
        super("targeted_nanites", ResourceHelper.texture("knowledge/targeted_nanites.png"), Nanites.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}