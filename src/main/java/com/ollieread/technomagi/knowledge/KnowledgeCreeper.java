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

public class KnowledgeCreeper extends Knowledge
{

    public static IResearchEvent attacked;
    public static IResearchEvent attack;
    public static IResearchEvent kill;
    public static IResearchAnalysis gunpowder;
    public static IResearchEvent killed;

    public KnowledgeCreeper(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        attacked = new ResearchEvent("attackingCreeper", getName(), 25, "attackedByCreeper", false, 2, null);
        attack = new ResearchEvent("attackedCreeper", getName(), 25, "attackedCreeper", false, 2, null);
        kill = new ResearchEvent("killedCreeper", getName(), 25, "killedCreeper", false, 2, null);
        gunpowder = new ResearchAnalysis("analyseGunpowder", getName(), 25, Arrays.asList(new ItemStack[] { new ItemStack(Items.gunpowder) }), false, 2, null);
        killed = new ResearchEvent("killedByCreeper", getName(), 50, "killedByCreeper", false, 1, null);
    }

}
