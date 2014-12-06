package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.knowledge.KnowledgeAggressive;
import com.ollieread.technomagi.knowledge.KnowledgeChicken;
import com.ollieread.technomagi.knowledge.KnowledgeCow;
import com.ollieread.technomagi.knowledge.KnowledgeCreeper;
import com.ollieread.technomagi.knowledge.KnowledgeDensity;
import com.ollieread.technomagi.knowledge.KnowledgeEnderman;
import com.ollieread.technomagi.knowledge.KnowledgeEndothermic;
import com.ollieread.technomagi.knowledge.KnowledgeExothermic;
import com.ollieread.technomagi.knowledge.KnowledgeForce;
import com.ollieread.technomagi.knowledge.KnowledgeForceII;
import com.ollieread.technomagi.knowledge.KnowledgeForceIII;
import com.ollieread.technomagi.knowledge.KnowledgeLife;
import com.ollieread.technomagi.knowledge.KnowledgeLifeII;
import com.ollieread.technomagi.knowledge.KnowledgeLifeIII;
import com.ollieread.technomagi.knowledge.KnowledgeLight;
import com.ollieread.technomagi.knowledge.KnowledgeNanites;
import com.ollieread.technomagi.knowledge.KnowledgePassive;
import com.ollieread.technomagi.knowledge.KnowledgePig;
import com.ollieread.technomagi.knowledge.KnowledgeSheep;
import com.ollieread.technomagi.knowledge.KnowledgeSkeleton;
import com.ollieread.technomagi.knowledge.KnowledgeTeleportation;
import com.ollieread.technomagi.knowledge.KnowledgeVoid;
import com.ollieread.technomagi.knowledge.KnowledgeZombie;

public class Knowledge
{

    public static IKnowledge knowledgeDensity;
    public static IKnowledge knowledgeEndothermic;
    public static IKnowledge knowledgeExothermic;
    public static IKnowledge knowledgeForce;
    public static IKnowledge knowledgeLight;
    public static IKnowledge knowledgeMotion;
    public static IKnowledge knowledgeProjectile;
    public static IKnowledge knowledgeSubspace;
    public static IKnowledge knowledgeTeleportation;
    public static IKnowledge knowledgeVoid;
    public static IKnowledge knowledgeNanites;
    public static IKnowledge knowledgeLife;
    public static IKnowledge knowledgeRebirth;
    public static IKnowledge knowledgeDeath;

    public static IKnowledge knowledgeCow;
    public static IKnowledge knowledgePig;
    public static IKnowledge knowledgeSheep;
    public static IKnowledge knowledgeChicken;
    public static IKnowledge knowledgePassive;

    public static IKnowledge knowledgeZombie;
    public static IKnowledge knowledgeSkeleton;
    public static IKnowledge knowledgeCreeper;
    public static IKnowledge knowledgeEnderman;
    public static IKnowledge knowledgeAggressive;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering knowledge");

        knowledgeDensity = new KnowledgeDensity("density");
        knowledgeEndothermic = new KnowledgeEndothermic("endothermic");
        knowledgeExothermic = new KnowledgeExothermic("exothermic");
        knowledgeForce = new KnowledgeForce("force");
        knowledgeLight = new KnowledgeLight("light");
        knowledgeMotion = new KnowledgeForceII("motion");
        knowledgeProjectile = new KnowledgeForceIII("projectile");
        // This is removed until I figure out wtf to do for it
        // Maybe something tying off the back of teleportation? I don't know
        // and I imagine no one will see this..
        // knowledgeSubspace = new KnowledgeSubspace("subspace");
        knowledgeTeleportation = new KnowledgeTeleportation("teleportation");
        knowledgeVoid = new KnowledgeVoid("void");
        knowledgeNanites = new KnowledgeNanites("nanites");
        knowledgeLife = new KnowledgeLife("life");
        knowledgeRebirth = new KnowledgeLifeII("rebirth");
        knowledgeDeath = new KnowledgeLifeIII("death");
        // Passive Knowledge
        knowledgeCow = new KnowledgeCow("entityCow");
        knowledgePig = new KnowledgePig("entityPig");
        knowledgeSheep = new KnowledgeSheep("entitySheep");
        knowledgeChicken = new KnowledgeChicken("entityChicken");
        knowledgePassive = new KnowledgePassive("entityPassive");
        // Aggressive Knowledge
        knowledgeZombie = new KnowledgeZombie("entityZombie");
        knowledgeSkeleton = new KnowledgeSkeleton("entitySkeleton");
        knowledgeCreeper = new KnowledgeCreeper("entityCreeper");
        knowledgeEnderman = new KnowledgeEnderman("entityEnderman");
        knowledgeAggressive = new KnowledgeAggressive("entityAggressive");
    }
}
