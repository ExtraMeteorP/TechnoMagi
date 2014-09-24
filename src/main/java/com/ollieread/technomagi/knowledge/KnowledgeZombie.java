package com.ollieread.technomagi.knowledge;

import java.util.Arrays;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchAnalysis;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeZombie extends Knowledge
{

    public static IResearchEvent attacked;
    public static IResearchEvent attack;
    public static IResearchEvent kill;
    public static IResearchAnalysis flesh;
    public static IResearchEvent killed;

    public KnowledgeZombie(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        attacked = new ResearchEvent("attackingZombie", getName(), 25, "attackedByZombie", false, 2, null);
        attack = new ResearchEvent("attackedZombie", getName(), 25, "attackedZombie", false, 2, null);
        kill = new ResearchEvent("killedZombie", getName(), 25, "killedZombie", false, 2, null);
        flesh = new ResearchAnalysis("analyseFlesh", getName(), 25, Arrays.asList(new ItemStack[] { new ItemStack(Items.rotten_flesh) }), false, 2, null);
        killed = new ResearchEvent("killedByZombie", getName(), 50, "killedByZombie", false, 1, null);
    }

}
