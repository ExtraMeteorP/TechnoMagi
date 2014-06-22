package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.api.research.IKnowledge;
import com.ollieread.technomagi.api.research.IResearch;
import com.ollieread.technomagi.research.KnowledgeFireResist;
import com.ollieread.technomagi.research.KnowledgeForceRedis;
import com.ollieread.technomagi.research.KnowledgeMagicResist;
import com.ollieread.technomagi.research.KnowledgePhysicalResist;
import com.ollieread.technomagi.research.KnowledgeProjectileResist;
import com.ollieread.technomagi.research.KnowledgeTeleportationI;
import com.ollieread.technomagi.research.KnowledgeTeleportationII;
import com.ollieread.technomagi.research.ResearchAnvilDamage;
import com.ollieread.technomagi.research.ResearchCactusDamage;
import com.ollieread.technomagi.research.ResearchFallingBlockDamage;
import com.ollieread.technomagi.research.ResearchInFireDamage;
import com.ollieread.technomagi.research.ResearchInLavaDamage;
import com.ollieread.technomagi.research.ResearchMagicDamage;
import com.ollieread.technomagi.research.ResearchOnFireDamage;
import com.ollieread.technomagi.research.ResearchStarveDamage;
import com.ollieread.technomagi.research.ResearchSuffocateDamage;
import com.ollieread.technomagi.research.ResearchToEnd;
import com.ollieread.technomagi.research.ResearchToNether;
import com.ollieread.technomagi.research.ResearchToOverworld;
import com.ollieread.technomagi.research.ResearchVoidDamage;
import com.ollieread.technomagi.research.ResearchWitherDamage;

public class Knowledge
{

    public static IKnowledge knowledgeFireResist;
    public static IKnowledge knowledgePhysicalResist;
    public static IKnowledge knowledgeProjectileResist;
    public static IKnowledge knowledgeMagicResist;
    public static IKnowledge knowledgeForceRedis;
    public static IKnowledge knowledgeTeleportationI;
    public static IKnowledge knowledgeTeleportationII;

    public static IResearch researchInFireDamage;
    public static IResearch researchOnFireDamage;
    public static IResearch researchInLavaDamage;
    public static IResearch researchAnvilDamage;
    public static IResearch researchCactusDamage;
    public static IResearch researchFallingBlockDamage;
    public static IResearch researchMagicDamage;
    public static IResearch researchStarveDamage;
    public static IResearch researchSuffocateDamage;
    public static IResearch researchVoidDamage;
    public static IResearch researchWitherDamage;
    public static IResearch researchToEnd;
    public static IResearch researchToNether;
    public static IResearch researchToOverworld;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering knowledge");

        // fire resist
        knowledgeFireResist = new KnowledgeFireResist("fireResist");
        researchInFireDamage = new ResearchInFireDamage("inFireDamage", knowledgeFireResist.getName(), 30);
        researchOnFireDamage = new ResearchOnFireDamage("onFireDamage", knowledgeFireResist.getName(), 40);
        researchInLavaDamage = new ResearchInLavaDamage("inLavaDamage", knowledgeFireResist.getName(), 30);

        // physical resist
        knowledgePhysicalResist = new KnowledgePhysicalResist("physicalResist");
        researchCactusDamage = new ResearchCactusDamage("cactusDamage", knowledgePhysicalResist.getName(), 30);
        researchSuffocateDamage = new ResearchSuffocateDamage("suffocateDamage", knowledgePhysicalResist.getName(), 30);
        researchStarveDamage = new ResearchStarveDamage("starveDamage", knowledgePhysicalResist.getName(), 30);

        // projectile resist
        knowledgeProjectileResist = new KnowledgeProjectileResist("projectileResist");

        // magic resist
        knowledgeMagicResist = new KnowledgeMagicResist("magicResist");
        researchMagicDamage = new ResearchMagicDamage("magicDamage", knowledgeMagicResist.getName(), 30);
        researchVoidDamage = new ResearchVoidDamage("voidDamage", knowledgeMagicResist.getName(), 30);
        researchWitherDamage = new ResearchWitherDamage("witherDamage", knowledgeMagicResist.getName(), 30);

        // force redistribution
        knowledgeForceRedis = new KnowledgeForceRedis("forceRedis");
        researchAnvilDamage = new ResearchAnvilDamage("anvilDamage", knowledgeForceRedis.getName(), 30);
        researchFallingBlockDamage = new ResearchFallingBlockDamage("fallingBlockDamage", knowledgeForceRedis.getName(), 30);

        // teleportation I
        knowledgeTeleportationI = new KnowledgeTeleportationI("teleportationI");

        // teleportation II
        knowledgeTeleportationII = new KnowledgeTeleportationII("teleportationII");
        researchToEnd = new ResearchToEnd("toEnd", knowledgeTeleportationII.getName(), 30);
        researchToNether = new ResearchToNether("toNether", knowledgeTeleportationII.getName(), 30);
        researchToOverworld = new ResearchToOverworld("toOverworld", knowledgeTeleportationII.getName(), 30);
    }

}
