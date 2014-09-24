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

public class KnowledgeSkeleton extends Knowledge
{

    public static IResearchEvent attacked;
    public static IResearchEvent attack;
    public static IResearchEvent kill;
    public static IResearchAnalysis bone;
    public static IResearchEvent killed;

    public KnowledgeSkeleton(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        attacked = new ResearchEvent("attackingSkeleton", getName(), 25, "attackedBySkeleton", false, 2, null);
        attack = new ResearchEvent("attackedSkeleton", getName(), 25, "attackedSkeleton", false, 2, null);
        kill = new ResearchEvent("killedSkeleton", getName(), 25, "killedSkeleton", false, 2, null);
        bone = new ResearchAnalysis("analyseBone", getName(), 25, Arrays.asList(new ItemStack[] { new ItemStack(Items.bone) }), false, 2, null);
        killed = new ResearchEvent("killedBySkeleton", getName(), 50, "killedBySkeleton", false, 1, null);
    }

}
