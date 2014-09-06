package com.ollieread.technomagi.research;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.analysis.ResearchAnalysisSpeedPotionI;
import com.ollieread.technomagi.research.analysis.ResearchAnalysisSpeedPotionII;

public class KnowledgeMotion extends Knowledge
{

    public static IResearchAnalysis analysisSpeedPotionI;
    public static IResearchAnalysis analysisSpeedPotionII;

    public KnowledgeMotion(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        analysisSpeedPotionI = new ResearchAnalysisSpeedPotionI("speedPotionI", getName(), 15, 1, new ArrayList(Arrays.asList(new ItemStack(net.minecraft.init.Items.potionitem, 1, 8194))));
        analysisSpeedPotionII = new ResearchAnalysisSpeedPotionII("speedPotionII", getName(), 15, 1, new ArrayList(Arrays.asList(new ItemStack(net.minecraft.init.Items.potionitem, 1, 8226))));
    }

}
