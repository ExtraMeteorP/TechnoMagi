package com.ollieread.technomagi.knowledge.cybermind;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.ennds.research.ShapelessAnalysisRecipe;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchAnalysis;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgePerception extends Knowledge
{

    public static IResearchEvent zombie;
    public static IResearchEvent skeleton;
    public static IResearchEvent creeper;
    public static IResearchEvent spider;
    public static IResearchEvent wolf;
    public static IResearchEvent enderman;

    public static IResearchAnalysis brainZombie;
    public static IResearchAnalysis brainSkeleton;
    public static IResearchAnalysis brainCreeper;
    public static IResearchAnalysis brainSpider;
    public static IResearchAnalysis brainWolf;
    public static IResearchAnalysis brainEnderman;

    public KnowledgePerception(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_CYBERMIND);

        zombie = new ResearchEvent("targetedByZombie", getName(), 2, EventHelper.entityTargeted(EntityZombie.class), 5, 16, new String[] {});
        skeleton = new ResearchEvent("targetedBySkeleton", getName(), 2, EventHelper.entityTargeted(EntitySkeleton.class), 5, 16, new String[] {});
        creeper = new ResearchEvent("targetedByCreeper", getName(), 2, EventHelper.entityTargeted(EntityCreeper.class), 5, 16, new String[] {});
        spider = new ResearchEvent("targetedBySpider", getName(), 2, EventHelper.entityTargeted(EntitySpider.class), 5, 12, new String[] {});
        wolf = new ResearchEvent("targetedByWolf", getName(), 2, EventHelper.entityTargeted(EntityWolf.class), 5, 8, new String[] {});
        enderman = new ResearchEvent("targetedByEnderman", getName(), 2, EventHelper.entityTargeted(EntityEnderman.class), 5, 8, new String[] {});

        brainZombie = new ResearchAnalysis("analyseZombieBrain", getName(), 8, new ShapelessAnalysisRecipe(ItemHelper.brain(EntityZombie.class)), 1, 4, new String[] {});
        brainSkeleton = new ResearchAnalysis("analyseSkeletonBrain", getName(), 8, new ShapelessAnalysisRecipe(ItemHelper.brain(EntitySkeleton.class)), 1, 4, new String[] {});
        brainCreeper = new ResearchAnalysis("analyseCreeperBrain", getName(), 8, new ShapelessAnalysisRecipe(ItemHelper.brain(EntityCreeper.class)), 1, 4, new String[] {});
        brainSpider = new ResearchAnalysis("analyseSpiderBrain", getName(), 8, new ShapelessAnalysisRecipe(ItemHelper.brain(EntitySpider.class)), 1, 4, new String[] {});
        brainWolf = new ResearchAnalysis("analyseWolfBrain", getName(), 8, new ShapelessAnalysisRecipe(ItemHelper.brain(EntityWolf.class)), 1, 4, new String[] {});
        brainEnderman = new ResearchAnalysis("analyseEndermanBrain", getName(), 8, new ShapelessAnalysisRecipe(ItemHelper.brain(EntityEnderman.class)), 1, 4, new String[] {});
    }

    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
