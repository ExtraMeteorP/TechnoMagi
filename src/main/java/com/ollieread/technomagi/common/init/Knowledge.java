package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgeBrainManipulation;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgeDataManipulation;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgeNaniteManipulation;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgePerception;
import com.ollieread.technomagi.knowledge.life.KnowledgeAggressive;
import com.ollieread.technomagi.knowledge.life.KnowledgeFlora;
import com.ollieread.technomagi.knowledge.life.KnowledgeLife;
import com.ollieread.technomagi.knowledge.life.KnowledgeMetabolism;
import com.ollieread.technomagi.knowledge.life.KnowledgePassive;
import com.ollieread.technomagi.knowledge.life.KnowledgeReproduction;
import com.ollieread.technomagi.knowledge.resources.KnowledgeCoal;
import com.ollieread.technomagi.knowledge.resources.KnowledgeGold;
import com.ollieread.technomagi.knowledge.resources.KnowledgeIron;
import com.ollieread.technomagi.knowledge.resources.KnowledgeProcessing;
import com.ollieread.technomagi.knowledge.resources.KnowledgeRedstone;
import com.ollieread.technomagi.knowledge.technology.KnowledgeEfficiency;
import com.ollieread.technomagi.knowledge.technology.KnowledgePhotoreactive;
import com.ollieread.technomagi.knowledge.technology.KnowledgePower;
import com.ollieread.technomagi.knowledge.technology.KnowledgeScanning;
import com.ollieread.technomagi.knowledge.technology.KnowledgeWarmth;

public class Knowledge
{

    // Technoloy
    public static IKnowledge power;
    public static IKnowledge augmentation;
    public static IKnowledge cyberlingualism;
    public static IKnowledge efficiency;
    public static IKnowledge possession;
    public static IKnowledge scanning;
    public static IKnowledge photoreactive;
    public static IKnowledge warmth;
    public static IKnowledge cold;

    // Cybermind
    public static IKnowledge brainManipulation;
    public static IKnowledge computerInteraction;
    public static IKnowledge dataManipulation;
    public static IKnowledge electrocommunication;
    public static IKnowledge hud;
    public static IKnowledge mindshifting;
    public static IKnowledge naniteManipulation;
    public static IKnowledge navigation;
    public static IKnowledge perception;

    // Abilities
    public static IKnowledge amplification;
    public static IKnowledge negation;

    // Life
    public static IKnowledge life;
    public static IKnowledge reproduction;
    public static IKnowledge passive;
    public static IKnowledge aggressive;
    public static IKnowledge flora;
    public static IKnowledge metabolism;

    // Resources
    public static IKnowledge coal;
    public static IKnowledge gold;
    public static IKnowledge iron;
    public static IKnowledge redstone;
    public static IKnowledge processing;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering knowledge");

        // Resources
        coal = new KnowledgeCoal("coal", new String[] {});
        gold = new KnowledgeGold("gold", new String[] {});
        iron = new KnowledgeIron("iron", new String[] {});
        redstone = new KnowledgeRedstone("redstone", new String[] {});
        processing = new KnowledgeProcessing("processing", new String[] { coal.getName(), gold.getName(), iron.getName(), redstone.getName() });

        // Life
        life = new KnowledgeLife("life", new String[] {});
        reproduction = new KnowledgeReproduction("reproduction", new String[] {});
        passive = new KnowledgePassive("passive", new String[] { life.getName() });
        aggressive = new KnowledgeAggressive("aggressive", new String[] { life.getName() });
        flora = new KnowledgeFlora("flora", new String[] { life.getName() });
        metabolism = new KnowledgeMetabolism("metabolism", new String[] { life.getName() });

        // Technology
        power = new KnowledgePower("power", new String[] {});
        efficiency = new KnowledgeEfficiency("efficiency", new String[] {});
        scanning = new KnowledgeScanning("scanning", new String[] {});
        photoreactive = new KnowledgePhotoreactive("photoreactive", new String[] { flora.getName(), power.getName() });
        warmth = new KnowledgeWarmth("warmth", new String[] {});

        // Cybermind
        dataManipulation = new KnowledgeDataManipulation("dataManipulation", new String[] {});
        naniteManipulation = new KnowledgeNaniteManipulation("naniteManipulation", new String[] {});
        perception = new KnowledgePerception("perception", new String[] { passive.getName(), aggressive.getName() });
        brainManipulation = new KnowledgeBrainManipulation("brainManipulation", new String[] {});

        // Casting
        // casting = new KnowledgeTechnology("casting", new String[] {});
        // amplification = new KnowledgeAmplification("amplification", new
        // String[] {});
        // negation = new KnowledgeNegation("negation", new String[] {});
    }
}
