package com.ollieread.technomagi.common.knowledge.nanites;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.common.knowledge.Nanites;
import com.ollieread.technomagi.util.ResourceHelper;

public class BasicNanites extends Knowledge
{

    public static Research unlockResearch;
    public static Research unlockKnowledge;

    public BasicNanites()
    {
        super("basic_nanites", ResourceHelper.texture("knowledge/basic_nanites.png"), Nanites.category.getName());

        TechnomagiApi.addKnowledge(this);

        unlockResearch = TechnomagiApi.addResearch("unlock_research", this.getName()).setProgress(5).setRepetition(20); // 100
        unlockKnowledge = TechnomagiApi.addResearch("unlock_research", this.getName()).setProgress(15).setRepetition(4); // 60
    }

    public void mappings()
    {
    }

}