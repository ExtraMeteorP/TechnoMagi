package com.ollieread.technomagi.knowledge.life;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;

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

public class KnowledgeAggressive extends Knowledge
{

    public static IResearchEvent attackedZombie;
    public static IResearchEvent attackedSkeleton;
    public static IResearchEvent attackedCreeper;
    public static IResearchEvent attackedSpider;

    public static IResearchEvent attackedByZombie;
    public static IResearchEvent attackedBySkeleton;
    public static IResearchEvent attackedByCreeper;
    public static IResearchEvent attackedBySpider;

    public static IResearchAnalysis brainZombie;
    public static IResearchAnalysis brainSkeleton;
    public static IResearchAnalysis brainCreeper;
    public static IResearchAnalysis brainSpider;

    public KnowledgeAggressive(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_LIFE);

        attackedZombie = new ResearchEvent("attackedZombie", getName(), 5, EventHelper.entityAttacked(EntityZombie.class), 1, 16, new String[] {});
        attackedSkeleton = new ResearchEvent("attackedSkeleton", getName(), 5, EventHelper.entityAttacked(EntitySkeleton.class), 1, 16, new String[] {});
        attackedCreeper = new ResearchEvent("attackedCreeper", getName(), 5, EventHelper.entityAttacked(EntityCreeper.class), 1, 16, new String[] {});
        attackedSpider = new ResearchEvent("attackedSpider", getName(), 5, EventHelper.entityAttacked(EntitySpider.class), 1, 16, new String[] {});

        attackedByZombie = new ResearchEvent("attackedByZombie", getName(), 5, EventHelper.entityAttackedBy(EntityZombie.class), 1, 16, new String[] {});
        attackedBySkeleton = new ResearchEvent("attackedBySkeleton", getName(), 5, EventHelper.entityAttackedBy(EntitySkeleton.class), 1, 16, new String[] {});
        attackedByCreeper = new ResearchEvent("attackedByCreeper", getName(), 5, EventHelper.entityAttackedBy(EntityCreeper.class), 1, 16, new String[] {});
        attackedBySpider = new ResearchEvent("attackedBySpider", getName(), 5, EventHelper.entityAttackedBy(EntitySpider.class), 1, 16, new String[] {});

        brainZombie = new ResearchAnalysis("analyseZombieBrain", getName(), 15, new ShapelessAnalysisRecipe(ItemHelper.brain(EntityZombie.class)), 1, 4, new String[] {});
        brainSkeleton = new ResearchAnalysis("analyseSkeletonBrain", getName(), 15, new ShapelessAnalysisRecipe(ItemHelper.brain(EntitySkeleton.class)), 1, 4, new String[] {});
        brainCreeper = new ResearchAnalysis("analyseCreeperBrain", getName(), 15, new ShapelessAnalysisRecipe(ItemHelper.brain(EntityCreeper.class)), 1, 4, new String[] {});
        brainSpider = new ResearchAnalysis("analyseSpiderBrain", getName(), 15, new ShapelessAnalysisRecipe(ItemHelper.brain(EntitySpider.class)), 1, 4, new String[] {});
    }

}
