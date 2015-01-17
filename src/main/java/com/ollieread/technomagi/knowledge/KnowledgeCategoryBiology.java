package com.ollieread.technomagi.knowledge;

import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.technomagi.knowledge.biology.KnowledgeBotany;
import com.ollieread.technomagi.knowledge.biology.KnowledgeBovine;
import com.ollieread.technomagi.knowledge.biology.KnowledgeOvisAries;

public class KnowledgeCategoryBiology
{

    public static IKnowledge botany;
    public static IKnowledge bovine;
    public static IKnowledge fowl;
    public static IKnowledge ovisAries;
    public static IKnowledge swine;
    public static IKnowledge procreation;
    public static IKnowledge metabolism;
    public static IKnowledge ethologyAggressive;
    public static IKnowledge ethologyPassive;
    public static IKnowledge perception;
    public static IKnowledge neurology;

    public String name;

    public KnowledgeCategoryBiology(String name)
    {
        this.name = name;

        botany = new KnowledgeBotany("botany", new String[] {}, getName());
        bovine = new KnowledgeBovine("bovine", new String[] {}, getName());
        fowl = new KnowledgeBotany("fowl", new String[] {}, getName());
        ovisAries = new KnowledgeOvisAries("ovisAries", new String[] {}, getName());
        swine = new KnowledgeBotany("swine", new String[] {}, getName());
        procreation = new KnowledgeBotany("procreation", new String[] {}, getName());
        metabolism = new KnowledgeBotany("metabolism", new String[] {}, getName());
        ethologyAggressive = new KnowledgeBotany("ethologyAggressive", new String[] {}, getName());
        ethologyPassive = new KnowledgeBotany("ethologyPassive", new String[] {}, getName());
        perception = new KnowledgeBotany("perception", new String[] {}, getName());
        neurology = new KnowledgeBotany("neurology", new String[] {}, getName());
    }

    public String getName()
    {
        return this.name;
    }

}
