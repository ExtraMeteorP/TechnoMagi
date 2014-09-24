package com.ollieread.technomagi.knowledge;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchMining;

public class KnowledgeDensity extends Knowledge
{

    public static IResearchMining coalore;
    public static IResearchMining ironore;
    public static IResearchMining goldore;
    public static IResearchMining diamondore;
    public static IResearchMining obsidian;

    public KnowledgeDensity(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        coalore = new ResearchMining("densityCoalOre", getName(), 20, new ItemStack(Blocks.coal_ore), false, 5, null);
        ironore = new ResearchMining("densityIronOre", getName(), 20, new ItemStack(Blocks.iron_ore), false, 4, null);
        goldore = new ResearchMining("densityGoldOre", getName(), 20, new ItemStack(Blocks.gold_ore), false, 3, null);
        diamondore = new ResearchMining("densityDiamond", getName(), 20, new ItemStack(Blocks.diamond_ore), false, 2, null);
        obsidian = new ResearchMining("densityObsidian", getName(), 20, new ItemStack(Blocks.obsidian), false, 2, null);
    }
}
