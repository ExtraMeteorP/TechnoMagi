package com.ollieread.technomagi.knowledge;

import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.technomagi.knowledge.technology.KnowledgeCompanionNanites;

public class KnowledgeCategoryTechnology
{

    public static IKnowledge companionNanites;
    public static IKnowledge efficiency;
    public static IKnowledge neurotronics;
    public static IKnowledge power;
    public static IKnowledge thermodynamics;

    public String name;

    public KnowledgeCategoryTechnology(String name)
    {
        this.name = name;

        companionNanites = new KnowledgeCompanionNanites("companionNanites", new String[] {}, getName());
    }

    public String getName()
    {
        return this.name;
    }

}
