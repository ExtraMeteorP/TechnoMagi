/**
 * 
 */
package com.ollieread.technomagi.knowledge.biology;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;

import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;

/**
 * @author ollieread
 *
 */
public class KnowledgePerception extends Knowledge
{

    public static IResearch targetedByPigmen;
    public static IResearch targetedByEnderman;
    public static IResearch targetedByWolf;
    public static IResearch targetedByCow;
    public static IResearch targetedByPig;
    public static IResearch targetedBySheep;
    public static IResearch zombieTargetsVillager;
    public static IResearch ironGolemTargetsZombie;
    public static IResearch wolfTargetsSheep;
    public static IResearch ocelotTargetsChicken;

    public KnowledgePerception(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);

        targetedByPigmen = new ResearchEvent("targetedByPigmen", getName(), EventHelper.entityTargeted(EntityPigZombie.class)).setProgress(3).setChance(6).setRepeatition(4).register();
        targetedByEnderman = new ResearchEvent("targetedByEnderman", getName(), EventHelper.entityTargeted(EntityEnderman.class)).setProgress(3).setChance(6).setRepeatition(4).register();
        targetedByWolf = new ResearchEvent("targetedByWolf", getName(), EventHelper.entityTargeted(EntityWolf.class)).setProgress(3).setChance(6).setRepeatition(4).register();
        targetedByCow = new ResearchEvent("targetedByCow", getName(), EventHelper.entityTargeted(EntityCow.class)).setProgress(3).setChance(6).setRepeatition(4).register();
        targetedByPig = new ResearchEvent("targetedByPig", getName(), EventHelper.entityTargeted(EntityPig.class)).setProgress(3).setChance(6).setRepeatition(4).register();
        targetedBySheep = new ResearchEvent("targetedBySheep", getName(), EventHelper.entityTargeted(EntitySheep.class)).setProgress(3).setChance(6).setRepeatition(4).register();

        zombieTargetsVillager = new ResearchEvent("zombieTargetsVillager", getName(), EventHelper.entityTargeted(EntityZombie.class, EntityVillager.class)).setProgress(5).setChance(2).setRepeatition(2).register();
        ironGolemTargetsZombie = new ResearchEvent("ironGolemTargetsZombie", getName(), EventHelper.entityTargeted(EntityIronGolem.class, EntityZombie.class)).setProgress(5).setChance(2).setRepeatition(2).register();
        wolfTargetsSheep = new ResearchEvent("wolfTargetsSheep", getName(), EventHelper.entityTargeted(EntityWolf.class, EntitySheep.class)).setProgress(5).setChance(2).setRepeatition(2).register();
        ocelotTargetsChicken = new ResearchEvent("ocelotTargetsChicken", getName(), EventHelper.entityTargeted(EntityOcelot.class, EntityChicken.class)).setProgress(5).setChance(2).setRepeatition(2).register();
    }

    @Override
    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
