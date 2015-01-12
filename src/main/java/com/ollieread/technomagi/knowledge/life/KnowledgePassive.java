package com.ollieread.technomagi.knowledge.life;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;

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

public class KnowledgePassive extends Knowledge
{

    public static IResearchEvent attackedPig;
    public static IResearchEvent attackedChicken;
    public static IResearchEvent attackedCow;
    public static IResearchEvent attackedSheep;
    public static IResearchAnalysis brainPig;
    public static IResearchAnalysis brainChicken;
    public static IResearchAnalysis brainCow;
    public static IResearchAnalysis brainSheep;

    public KnowledgePassive(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_LIFE);

        attackedPig = new ResearchEvent("attackedPig", getName(), 10, EventHelper.entityAttacked(EntityPig.class), 1, 24, new String[] {});
        attackedChicken = new ResearchEvent("attackedChicken", getName(), 10, EventHelper.entityAttacked(EntityChicken.class), 1, 24, new String[] {});
        attackedCow = new ResearchEvent("attackedCow", getName(), 10, EventHelper.entityAttacked(EntityCow.class), 1, 24, new String[] {});
        attackedSheep = new ResearchEvent("attackedSheep", getName(), 10, EventHelper.entityAttacked(EntitySheep.class), 1, 24, new String[] {});

        brainPig = new ResearchAnalysis("analysePigBrain", getName(), 15, new ShapelessAnalysisRecipe(ItemHelper.brain(EntityPig.class)), 1, 4);
        brainChicken = new ResearchAnalysis("analyseChickenBrain", getName(), 15, new ShapelessAnalysisRecipe(ItemHelper.brain(EntityChicken.class)), 1, 4);
        brainCow = new ResearchAnalysis("analyseCowBrain", getName(), 15, new ShapelessAnalysisRecipe(ItemHelper.brain(EntityCow.class)), 1, 4);
        brainSheep = new ResearchAnalysis("analyseSheepBrain", getName(), 15, new ShapelessAnalysisRecipe(ItemHelper.brain(EntitySheep.class)), 1, 4);
    }

    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
