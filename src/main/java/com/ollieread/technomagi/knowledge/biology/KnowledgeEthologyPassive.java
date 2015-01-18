/**
 * 
 */
package com.ollieread.technomagi.knowledge.biology;

import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;

import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.KnowledgeCategoryBiology;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;

/**
 * Ethology Passive
 * 
 * Possible 145 points of data available.
 * 
 * @author ollieread
 *
 */
public class KnowledgeEthologyPassive extends Knowledge
{

    public static IResearch bovine;
    public static IResearch swine;
    public static IResearch ovisaries;
    public static IResearch fowl;

    public static IResearch attackedSquid;
    public static IResearch killedSquid;
    public static IResearch attackedBat;
    public static IResearch killedBat;
    public static IResearch attackedHorse;
    public static IResearch killedHorse;
    public static IResearch attackedVillager;
    public static IResearch killedVillager;
    public static IResearch targettedByIronGolem;
    public static IResearch attackedByIronGolem;

    public KnowledgeEthologyPassive(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);

        bovine = new ResearchEvent("bovine", getName(), EventHelper.knowledge(KnowledgeCategoryBiology.bovine), true).setProgress(15);
        swine = new ResearchEvent("swine", getName(), EventHelper.knowledge(KnowledgeCategoryBiology.swine), true).setProgress(15);
        ovisaries = new ResearchEvent("ovisaries", getName(), EventHelper.knowledge(KnowledgeCategoryBiology.ovisAries), true).setProgress(15);
        fowl = new ResearchEvent("fowl", getName(), EventHelper.knowledge(KnowledgeCategoryBiology.fowl), true).setProgress(15);

        attackedSquid = new ResearchEvent("attackedSquid", getName(), EventHelper.entityAttacked(EntitySquid.class)).setProgress(3).setChance(5).setRepeatition(3);
        killedSquid = new ResearchEvent("killedSquid", getName(), EventHelper.entityKilled(EntitySquid.class)).setProgress(8).setChance(6);
        attackedBat = new ResearchEvent("attackedBat", getName(), EventHelper.entityAttacked(EntityBat.class)).setProgress(3).setChance(5).setRepeatition(3);
        killedBat = new ResearchEvent("killedBat", getName(), EventHelper.entityKilled(EntityBat.class)).setProgress(8).setChance(6);
        attackedHorse = new ResearchEvent("attackedHorse", getName(), EventHelper.entityAttacked(EntityHorse.class)).setProgress(3).setChance(5).setRepeatition(3);
        killedHorse = new ResearchEvent("killedHorse", getName(), EventHelper.entityKilled(EntityHorse.class)).setProgress(8).setChance(6);
        attackedVillager = new ResearchEvent("attackedVillager", getName(), EventHelper.entityAttacked(EntityVillager.class)).setProgress(3).setChance(5).setRepeatition(3);
        killedVillager = new ResearchEvent("killedVillager", getName(), EventHelper.entityKilled(EntityVillager.class)).setProgress(8).setChance(6);
        targettedByIronGolem = new ResearchEvent("targettedByIronGolem", getName(), EventHelper.entityAttacked(EntityIronGolem.class)).setProgress(3).setChance(5).setRepeatition(3);
        attackedByIronGolem = new ResearchEvent("attackedByIronGolem", getName(), EventHelper.entityKilled(EntityIronGolem.class)).setProgress(8).setChance(6);
    }

    @Override
    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
