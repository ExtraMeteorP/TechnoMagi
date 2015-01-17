/**
 * 
 */
package com.ollieread.technomagi.knowledge.biology;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;

import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;

/**
 * @author ollieread
 *
 */
public class KnowledgeProcreation extends Knowledge
{

    // Cow
    public static IResearch temptCow;
    public static IResearch breedCow;
    public static IResearch birthCow;
    // Sheep
    public static IResearch temptSheep;
    public static IResearch breedSheep;
    public static IResearch birthSheep;
    // Pig
    public static IResearch temptPig;
    public static IResearch breedPig;
    public static IResearch birthPig;
    // Chicken
    public static IResearch temptChicken;
    public static IResearch breedChicken;
    public static IResearch birthChicken;

    public KnowledgeProcreation(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);

        // Cow
        temptCow = new ResearchEvent("temptCow", getName(), EventHelper.entityTempt(EntityCow.class)).setProgress(5).setChance(6);
        breedCow = new ResearchEvent("breedCow", getName(), EventHelper.entityBreed(EntityCow.class)).setProgress(10).setChance(6);
        birthCow = new ResearchEvent("birthCow", getName(), EventHelper.entityBirth(EntityCow.class)).setProgress(10).setChance(6);
        // Sheep
        temptSheep = new ResearchEvent("temptSheep", getName(), EventHelper.entityTempt(EntitySheep.class)).setProgress(5).setChance(6);
        breedSheep = new ResearchEvent("breedSheep", getName(), EventHelper.entityBreed(EntitySheep.class)).setProgress(10).setChance(6);
        birthSheep = new ResearchEvent("birthSheep", getName(), EventHelper.entityBirth(EntitySheep.class)).setProgress(10).setChance(6);
        // Pig
        temptPig = new ResearchEvent("temptPig", getName(), EventHelper.entityTempt(EntityPig.class)).setProgress(5).setChance(6);
        breedPig = new ResearchEvent("breedPig", getName(), EventHelper.entityBreed(EntityPig.class)).setProgress(10).setChance(6);
        birthPig = new ResearchEvent("birthPig", getName(), EventHelper.entityBirth(EntityPig.class)).setProgress(10).setChance(6);
        // Chicken
        temptChicken = new ResearchEvent("temptChicken", getName(), EventHelper.entityTempt(EntityChicken.class)).setProgress(5).setChance(6);
        breedChicken = new ResearchEvent("breedChicken", getName(), EventHelper.entityBreed(EntityChicken.class)).setProgress(10).setChance(6);
        birthChicken = new ResearchEvent("birthChicken", getName(), EventHelper.entityBirth(EntityChicken.class)).setProgress(10).setChance(6);
    }

    @Override
    public String[] getIntrigue()
    {
        return null;
    }

}
