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
import com.ollieread.technomagi.common.Information;
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

    public static IResearch targetedByCreeper;
    public static IResearch targetedBySkeleton;
    public static IResearch targetedByZombie;
    public static IResearch targetedBySpider;
    public static IResearch targetedBySlime;
    public static IResearch targetedByEnderman;

    public static IResearch attackedByCreeper;
    public static IResearch attackedBySkeleton;
    public static IResearch attackedByZombie;
    public static IResearch attackedBySpider;
    public static IResearch attackedBySlime;
    public static IResearch attackedByEnderman;

    public KnowledgeEthologyAggressive(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);

        targetedByCreeper = new ResearchEvent("targetedByCreeper", getName(), EventHelper.entityTargeted(EntityCreeper.class)).setProgress(1).setChance(6).setRepeatition(10).register();
        targetedBySkeleton = new ResearchEvent("targetedBySkeleton", getName(), EventHelper.entityTargeted(EntitySkeleton.class)).setProgress(1).setChance(6).setRepeatition(10).register();
        targetedByZombie = new ResearchEvent("targetedByZombie", getName(), EventHelper.entityTargeted(EntityZombie.class)).setProgress(1).setChance(6).setRepeatition(10).register();
        targetedBySpider = new ResearchEvent("targetedBySpider", getName(), EventHelper.entityTargeted(EntitySpider.class)).setProgress(1).setChance(6).setRepeatition(10).register();
        targetedBySlime = new ResearchEvent("targetedBySlime", getName(), EventHelper.entityTargeted(EntitySlime.class)).setProgress(1).setChance(6).setRepeatition(10).register();
        targetedByEnderman = new ResearchEvent("targetedByEnderman", getName(), EventHelper.entityTargeted(EntityEnderman.class)).setProgress(1).setChance(6).setRepeatition(10).register();

        attackedByCreeper = new ResearchEvent("attackedByCreeper", getName(), EventHelper.entityAttackedBy(EntityCreeper.class)).setProgress(2).setChance(8).setRepeatition(6).register();
        attackedBySkeleton = new ResearchEvent("attackedBySkeleton", getName(), EventHelper.entityAttackedBy(EntitySkeleton.class)).setProgress(2).setChance(8).setRepeatition(6).register();
        attackedByZombie = new ResearchEvent("attackedByZombie", getName(), EventHelper.entityAttackedBy(EntityZombie.class)).setProgress(2).setChance(8).setRepeatition(6).register();
        attackedBySpider = new ResearchEvent("attackedBySpider", getName(), EventHelper.entityAttackedBy(EntitySpider.class)).setProgress(2).setChance(8).setRepeatition(6).register();
        attackedBySlime = new ResearchEvent("attackedBySlime", getName(), EventHelper.entityAttackedBy(EntitySlime.class)).setProgress(2).setChance(8).setRepeatition(6).register();
        attackedByEnderman = new ResearchEvent("attackedByEnderman", getName(), EventHelper.entityAttackedBy(EntityEnderman.class)).setProgress(2).setChance(8).setRepeatition(6).register();
    }

    @Override
    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
