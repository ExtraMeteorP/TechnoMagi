package com.ollieread.technomagi.knowledge;

import java.util.Arrays;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchObservation;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchAnalysis;
import com.ollieread.technomagi.knowledge.research.ResearchObservation;

public class KnowledgeForceII extends Knowledge
{

    public static IResearchAnalysis sugar;
    public static IResearchAnalysis potionSwiftness;
    public static IResearchAnalysis potionSlowness;
    public static IResearchAnalysis sugarSpiderEye;
    public static IResearchObservation villagerSwiftness;
    public static IResearchObservation villagerSlowness;
    public static IResearchObservation villagerTest;
    public static IResearchObservation villagerTest2;
    public static IResearchObservation villagerTest3;
    public static IResearchObservation villagerTest4;
    public static IResearchObservation villagerTest5;
    public static IResearchObservation villagerTest6;
    public static IResearchObservation villagerTest7;
    public static IResearchObservation villagerTest8;
    public static IResearchObservation villagerTest9;
    public static IResearchObservation villagerTest10;
    public static IResearchObservation villagerTest11;

    public KnowledgeForceII(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        sugar = new ResearchAnalysis("analyseSugarMotion", getName(), 15, Arrays.asList(new ItemStack[] { new ItemStack(Items.sugar) }), false, 3, new String[] { "force" });
        potionSwiftness = new ResearchAnalysis("analyseSwiftnessMotion", getName(), 15, Arrays.asList(new ItemStack[] { new ItemStack(Items.potionitem, 1, 8194) }), false, 2, new String[] { "force" });
        potionSlowness = new ResearchAnalysis("analyseSlownessMotion", getName(), 15, Arrays.asList(new ItemStack[] { new ItemStack(Items.potionitem, 1, 8202) }), false, 2, new String[] { "force" });
        sugarSpiderEye = new ResearchAnalysis("analyseSugarSpiderEyeMotion", getName(), 15, Arrays.asList(new ItemStack[] { new ItemStack(Items.sugar), new ItemStack(Items.fermented_spider_eye) }), false, 3, new String[] { "force" });
        villagerSwiftness = new ResearchObservation("villagerSwiftness", EntityVillager.class, getName(), 20, new ItemStack(Items.potionitem, 1, 8194), false, 2, new String[] { "force" }, -10);
        villagerSlowness = new ResearchObservation("villagerSlowness", EntityVillager.class, getName(), 20, new ItemStack(Items.potionitem, 1, 8202), false, 2, new String[] { "force" }, -10);
    }

}
