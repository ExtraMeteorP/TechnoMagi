package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.research.KnowledgeAggressive;
import com.ollieread.technomagi.research.KnowledgeChicken;
import com.ollieread.technomagi.research.KnowledgeCow;
import com.ollieread.technomagi.research.KnowledgeCreeper;
import com.ollieread.technomagi.research.KnowledgeDensity;
import com.ollieread.technomagi.research.KnowledgeEnderman;
import com.ollieread.technomagi.research.KnowledgeEndothermic;
import com.ollieread.technomagi.research.KnowledgeExothermic;
import com.ollieread.technomagi.research.KnowledgeForce;
import com.ollieread.technomagi.research.KnowledgeLight;
import com.ollieread.technomagi.research.KnowledgeMotion;
import com.ollieread.technomagi.research.KnowledgePassive;
import com.ollieread.technomagi.research.KnowledgePig;
import com.ollieread.technomagi.research.KnowledgeProjectile;
import com.ollieread.technomagi.research.KnowledgeSheep;
import com.ollieread.technomagi.research.KnowledgeSkeleton;
import com.ollieread.technomagi.research.KnowledgeSubspace;
import com.ollieread.technomagi.research.KnowledgeTeleportation;
import com.ollieread.technomagi.research.KnowledgeVoid;
import com.ollieread.technomagi.research.KnowledgeZombie;

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
        knowledgeMotion = new KnowledgeMotion("motion");
        knowledgeProjectile = new KnowledgeProjectile("projectile");
        knowledgeSubspace = new KnowledgeSubspace("subspace");
        knowledgeTeleportation = new KnowledgeTeleportation("teleportation");
        knowledgeVoid = new KnowledgeVoid("void");

        knowledgeCow = new KnowledgeCow("entityCow");
        knowledgePig = new KnowledgePig("entityPig");
        knowledgeSheep = new KnowledgeSheep("entitySheep");
        knowledgeChicken = new KnowledgeChicken("entityChicken");
        knowledgePassive = new KnowledgePassive("entityPassive");

        knowledgeZombie = new KnowledgeZombie("entityZombie");
        knowledgeSkeleton = new KnowledgeSkeleton("entitySkeleton");
        knowledgeCreeper = new KnowledgeCreeper("entityCreeper");
        knowledgeEnderman = new KnowledgeEnderman("entityEnderman");
        knowledgeAggressive = new KnowledgeAggressive("entityAggressive");
    }
}
