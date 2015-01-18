/**
 * 
 */
package com.ollieread.technomagi.knowledge.biology;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;

import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;

/**
 * Ethology Aggressive
 * 
 * Possible 122 points of data available. Any 10 of 12 required.
 * 
 * @author ollieread
 *
 */
public class KnowledgeEthologyAggressive extends Knowledge
{

    public static IResearch targettedByCreeper;
    public static IResearch targettedBySkeleton;
    public static IResearch targettedByZombie;
    public static IResearch targettedBySpider;
    public static IResearch targettedBySlime;
    public static IResearch targettedByEnderman;

    public static IResearch attackedByCreeper;
    public static IResearch attackedBySkeleton;
    public static IResearch attackedByZombie;
    public static IResearch attackedBySpider;
    public static IResearch attackedBySlime;
    public static IResearch attackedByEnderman;

    public KnowledgeEthologyAggressive(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);

        targettedByCreeper = new ResearchEvent("targettedByCreeper", getName(), EventHelper.entityTargeted(EntityCreeper.class)).setProgress(1).setChance(6).setRepeatition(10);
        targettedBySkeleton = new ResearchEvent("targettedBySkeleton", getName(), EventHelper.entityTargeted(EntitySkeleton.class)).setProgress(1).setChance(6).setRepeatition(10);
        targettedByZombie = new ResearchEvent("targettedByZombie", getName(), EventHelper.entityTargeted(EntityZombie.class)).setProgress(1).setChance(6).setRepeatition(10);
        targettedBySpider = new ResearchEvent("targettedBySpider", getName(), EventHelper.entityTargeted(EntitySpider.class)).setProgress(1).setChance(6).setRepeatition(10);
        targettedBySlime = new ResearchEvent("targettedBySlime", getName(), EventHelper.entityTargeted(EntitySlime.class)).setProgress(1).setChance(6).setRepeatition(10);
        targettedByEnderman = new ResearchEvent("targettedByEnderman", getName(), EventHelper.entityTargeted(EntityEnderman.class)).setProgress(1).setChance(6).setRepeatition(10);

        attackedByCreeper = new ResearchEvent("attackedByCreeper", getName(), EventHelper.entityAttackedBy(EntityCreeper.class)).setProgress(2).setChance(8).setRepeatition(6);
        attackedBySkeleton = new ResearchEvent("attackedBySkeleton", getName(), EventHelper.entityAttackedBy(EntitySkeleton.class)).setProgress(2).setChance(8).setRepeatition(6);
        attackedByZombie = new ResearchEvent("attackedByZombie", getName(), EventHelper.entityAttackedBy(EntityZombie.class)).setProgress(2).setChance(8).setRepeatition(6);
        attackedBySpider = new ResearchEvent("attackedBySpider", getName(), EventHelper.entityAttackedBy(EntitySpider.class)).setProgress(2).setChance(8).setRepeatition(6);
        attackedBySlime = new ResearchEvent("attackedBySlime", getName(), EventHelper.entityAttackedBy(EntitySlime.class)).setProgress(2).setChance(8).setRepeatition(6);
        attackedByEnderman = new ResearchEvent("attackedByEnderman", getName(), EventHelper.entityAttackedBy(EntityEnderman.class)).setProgress(2).setChance(8).setRepeatition(6);
    }

    @Override
    public String[] getIntrigue()
    {
        return null;
    }

}
