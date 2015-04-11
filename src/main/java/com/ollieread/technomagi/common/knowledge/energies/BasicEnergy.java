package com.ollieread.technomagi.common.knowledge.energies;

import net.minecraft.init.Blocks;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.common.knowledge.Energies;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.ResourceHelper;

public class BasicEnergy extends Knowledge
{

    public static Research mineRedstoneOre;
    public static Research analyseRedstoneOre;
    public static Research analyseRedstoneDust;
    public static Research craftRedstoneBlock;
    public static Research analyseRedstoneBlock;

    public BasicEnergy()
    {
        super("basic_energy", ResourceHelper.texture("knowledge/basic_energy.png"), Energies.category.getName());

        // Add prerequisites
        // addPrerequisite(blah);

        // Register
        TechnomagiApi.addKnowledge(this);

        // Research
        mineRedstoneOre = TechnomagiApi.addResearch("mine_redstone_ore", getName()).setProgress(5).setChance(5).setRepetition(4); // 20
        analyseRedstoneOre = TechnomagiApi.addResearch("analyse_redstone_ore", getName()).setProgress(4).setChance(4).setRepetition(4); // 20
        analyseRedstoneDust = TechnomagiApi.addResearch("analyse_redstone_dust", getName()).setProgress(5).setChance(4).setRepetition(5); // 25
        craftRedstoneBlock = TechnomagiApi.addResearch("craft_redstone_block", getName()).setProgress(5).setChance(8).setRepetition(4); // 20
        analyseRedstoneBlock = TechnomagiApi.addResearch("analyse_redstone_block", getName()).setProgress(6).setChance(4).setRepetition(3); // 18
    }

    public void mappings()
    {
        // Add scan and analysis mappings
        TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.item("redstone_ore")), analyseRedstoneOre.getName());
        TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.item("redstone")), analyseRedstoneDust.getName());
        TechnomagiApi.scan().addAnalysisMapping(ItemStackHelper.getItemStackRepresentation(ItemStackHelper.item("redstone_block")), analyseRedstoneBlock.getName());
        // Add event mappings
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("redstone_block"), craftRedstoneBlock.getName());
        TechnomagiApi.knowledge().mapMiningResearch(Blocks.lit_redstone_ore, 0, mineRedstoneOre.getName());
    }

}
