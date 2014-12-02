package com.ollieread.technomagi.knowledge;

import java.util.Arrays;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchAnalysis;

public class KnowledgeMotion extends Knowledge
{

    public static IResearchAnalysis sugar;
    public static IResearchAnalysis potionSwiftness;
    public static IResearchAnalysis potionSlowness;
    public static IResearchAnalysis sugarSpiderEye;

    public KnowledgeMotion(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        sugar = new ResearchAnalysis("analyseSugarMotion", getName(), 25, Arrays.asList(new ItemStack[] { new ItemStack(Items.sugar) }), false, 3, new String[] { "force" });
        potionSwiftness = new ResearchAnalysis("analyseSwiftnessMotion", getName(), 25, Arrays.asList(new ItemStack[] { new ItemStack(Items.potionitem, 1, 8194) }), false, 2, new String[] { "force" });
        potionSlowness = new ResearchAnalysis("analyseSlownessMotion", getName(), 25, Arrays.asList(new ItemStack[] { new ItemStack(Items.potionitem, 1, 8202) }), false, 2, new String[] { "force" });
        sugarSpiderEye = new ResearchAnalysis("analyseSugarSpiderEyeMotion", getName(), 25, Arrays.asList(new ItemStack[] { new ItemStack(Items.sugar), new ItemStack(Items.fermented_spider_eye) }), false, 3, new String[] { "force" });
    }

}
