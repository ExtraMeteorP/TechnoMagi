package com.ollieread.technomagi.knowledge.life;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;

public class KnowledgeReproduction extends Knowledge
{

    public static IResearchEvent breedChicken;
    public static IResearchEvent breedPig;
    public static IResearchEvent breedCow;
    public static IResearchEvent breedSheep;
    public static IResearchEvent birthChicken;
    public static IResearchEvent birthPig;
    public static IResearchEvent birthCow;
    public static IResearchEvent birthSheep;

    public KnowledgeReproduction(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_LIFE);

        breedChicken = new ResearchEvent("breedChicken", getName(), 10, EventHelper.entityBreed(EntityChicken.class), 1, 6, new String[] {});
        breedPig = new ResearchEvent("breedPig", getName(), 10, EventHelper.entityBreed(EntityPig.class), 1, 6, new String[] {});
        breedCow = new ResearchEvent("breedCow", getName(), 10, EventHelper.entityBreed(EntityCow.class), 1, 6, new String[] {});
        breedSheep = new ResearchEvent("breedSheep", getName(), 10, EventHelper.entityBreed(EntitySheep.class), 1, 6, new String[] {});

        birthChicken = new ResearchEvent("birthChicken", getName(), 15, EventHelper.entityBirth(EntityChicken.class), 1, 6, new String[] { breedChicken.getName() });
        birthPig = new ResearchEvent("birthPig", getName(), 15, EventHelper.entityBirth(EntityPig.class), 1, 6, new String[] { breedPig.getName() });
        birthCow = new ResearchEvent("birthCow", getName(), 15, EventHelper.entityBirth(EntityCow.class), 1, 6, new String[] { breedCow.getName() });
        birthSheep = new ResearchEvent("birthSheep", getName(), 15, EventHelper.entityBirth(EntitySheep.class), 1, 6, new String[] { breedSheep.getName() });
    }

    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
