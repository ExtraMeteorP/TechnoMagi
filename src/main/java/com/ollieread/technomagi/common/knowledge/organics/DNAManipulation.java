package com.ollieread.technomagi.common.knowledge.organics;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Organics;
import com.ollieread.technomagi.util.ResourceHelper;

public class DNAManipulation extends Knowledge
{

    public DNAManipulation()
    {
        super("dna_manipulation", ResourceHelper.texture("knowledge/dna_manipulation.png"), Organics.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}