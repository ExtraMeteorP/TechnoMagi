package com.ollieread.technomagi.knowledge;

import java.util.Arrays;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchAnalysis;

public class KnowledgeLife extends Knowledge
{

    public static ItemStack[] saplingList;
    public static ItemStack[] seedList;
    public static ItemStack[] leafList;

    public static IResearchAnalysis egg;
    public static IResearchAnalysis saplings;
    public static IResearchAnalysis seeds;
    public static IResearchAnalysis leaves;

    public KnowledgeLife(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        saplingList = new ItemStack[] { new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Blocks.sapling, 1, 1), new ItemStack(Blocks.sapling, 1, 2), new ItemStack(Blocks.sapling, 1, 3) };
        seedList = new ItemStack[] { new ItemStack(Items.wheat_seeds), new ItemStack(Items.melon_seeds), new ItemStack(Items.pumpkin_seeds) };
        leafList = new ItemStack[] { new ItemStack(Blocks.leaves, 1, 0), new ItemStack(Blocks.leaves, 1, 1), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1, 3) };

        egg = new ResearchAnalysis("lifeEgg", getName(), 25, Arrays.asList(new ItemStack[] { new ItemStack(Items.egg) }), false, 4, null);
        saplings = new ResearchAnalysis("lifeSaplings", getName(), 25, Arrays.asList(saplingList), false, 4, null);
        seeds = new ResearchAnalysis("lifeSeeds", getName(), 25, Arrays.asList(seedList), false, 4, null);
        leaves = new ResearchAnalysis("lifeLeaves", getName(), 25, Arrays.asList(leafList), false, 4, null);
    }

}
