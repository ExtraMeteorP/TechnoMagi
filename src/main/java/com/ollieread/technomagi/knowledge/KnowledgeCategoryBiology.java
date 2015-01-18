package com.ollieread.technomagi.knowledge;

import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.technomagi.knowledge.biology.KnowledgeBotany;
import com.ollieread.technomagi.knowledge.biology.KnowledgeBovine;
import com.ollieread.technomagi.knowledge.biology.KnowledgeEthologyAggressive;
import com.ollieread.technomagi.knowledge.biology.KnowledgeEthologyPassive;
import com.ollieread.technomagi.knowledge.biology.KnowledgeFowl;
import com.ollieread.technomagi.knowledge.biology.KnowledgeMetabolism;
import com.ollieread.technomagi.knowledge.biology.KnowledgeNeurology;
import com.ollieread.technomagi.knowledge.biology.KnowledgeOvisAries;
import com.ollieread.technomagi.knowledge.biology.KnowledgePerception;
import com.ollieread.technomagi.knowledge.biology.KnowledgeProcreation;
import com.ollieread.technomagi.knowledge.biology.KnowledgeSwine;

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
        fowl = new KnowledgeFowl("fowl", new String[] {}, getName());
        ovisAries = new KnowledgeOvisAries("ovisAries", new String[] {}, getName());
        swine = new KnowledgeSwine("swine", new String[] {}, getName());
        procreation = new KnowledgeProcreation("procreation", new String[] {}, getName());
        metabolism = new KnowledgeMetabolism("metabolism", new String[] {}, getName());
        ethologyAggressive = new KnowledgeEthologyAggressive("ethologyAggressive", new String[] {}, getName());
        ethologyPassive = new KnowledgeEthologyPassive("ethologyPassive", new String[] {}, getName());
        perception = new KnowledgePerception("perception", new String[] {}, getName());
        neurology = new KnowledgeNeurology("neurology", new String[] {}, getName());
    }

    public String getName()
    {
        return this.name;
    }

}
