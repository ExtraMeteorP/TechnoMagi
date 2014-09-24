package com.ollieread.technomagi.knowledge;

import java.util.Arrays;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchAnalysis;
import com.ollieread.technomagi.knowledge.research.ResearchCrafting;

public class KnowledgeLight extends Knowledge
{

    public static IResearchCrafting craftTorch;
    public static IResearchCrafting craftLamp;
    public static IResearchAnalysis analyseTorch;
    public static IResearchAnalysis analyseLamp;

    public KnowledgeLight(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        craftTorch = new ResearchCrafting("craftingTorchLight", getName(), 25, new ItemStack(Blocks.torch), false, 2, null);
        craftLamp = new ResearchCrafting("craftingRedstoneLampLight", getName(), 25, new ItemStack(Blocks.redstone_lamp), false, 2, null);
        analyseTorch = new ResearchAnalysis("analyseTorchLight", getName(), 25, Arrays.asList(new ItemStack[] { new ItemStack(Blocks.torch) }), false, 2, null);
        analyseTorch = new ResearchAnalysis("analyseRedstoneLampLight", getName(), 25, Arrays.asList(new ItemStack[] { new ItemStack(Blocks.redstone_lamp) }), false, 2, null);
    }

}
