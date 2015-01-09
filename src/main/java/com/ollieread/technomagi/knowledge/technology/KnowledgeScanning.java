package com.ollieread.technomagi.knowledge.technology;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.ennds.research.ShapelessAnalysisRecipe;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchAnalysis;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgeScanning extends Knowledge
{

    public static IResearchAnalysis spidereye;
    public static IResearchEvent endermanTarget;
    public static IResearchAnalysis spiderBrain;
    public static IResearchAnalysis skeletonBrain;
    public static IResearchAnalysis endermanBrain;

    public KnowledgeScanning(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_TECHNOLOGY);

        spidereye = new ResearchAnalysis("spidereye", getName(), 20, new ShapelessAnalysisRecipe(ItemHelper.item("spider_eye")), 1, 4);
        endermanTarget = new ResearchEvent("endermanTarget", getName(), 20, EventHelper.entityTargeted(EntityEnderman.class), 1, 8, new String[] {});
        spiderBrain = new ResearchAnalysis("spiderBrain", getName(), 20, new ShapelessAnalysisRecipe(ItemHelper.brain(EntitySpider.class)), 1, 6, new String[] { spidereye.getName() });
        skeletonBrain = new ResearchAnalysis("skeletonBrain", getName(), 20, new ShapelessAnalysisRecipe(ItemHelper.brain(EntitySpider.class)), 1, 6);
        endermanBrain = new ResearchAnalysis("endermanBrain", getName(), 20, new ShapelessAnalysisRecipe(ItemHelper.brain(EntityEnderman.class)), 1, 6, new String[] { endermanTarget.getName() });
    }
}