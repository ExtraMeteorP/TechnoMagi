package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.knowledge.casting.KnowledgeAmplification;
import com.ollieread.technomagi.knowledge.casting.KnowledgeNegation;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgeBrainManipulation;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgeComputerInteraction;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgeCybermind;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgeDataManipulation;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgeElectrocommunication;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgeHUD;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgeMindshifting;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgeNaniteManipulation;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgeNavigation;
import com.ollieread.technomagi.knowledge.cybermind.KnowledgePerception;
import com.ollieread.technomagi.knowledge.general.KnowledgeGeneral;
import com.ollieread.technomagi.knowledge.life.KnowledgeAggressive;
import com.ollieread.technomagi.knowledge.life.KnowledgeFlora;
import com.ollieread.technomagi.knowledge.life.KnowledgeLife;
import com.ollieread.technomagi.knowledge.life.KnowledgeMetabolism;
import com.ollieread.technomagi.knowledge.life.KnowledgePassive;
import com.ollieread.technomagi.knowledge.life.KnowledgeReproduction;
import com.ollieread.technomagi.knowledge.resources.KnowledgeCoal;
import com.ollieread.technomagi.knowledge.resources.KnowledgeGold;
import com.ollieread.technomagi.knowledge.resources.KnowledgeIron;
import com.ollieread.technomagi.knowledge.resources.KnowledgeRedstone;
import com.ollieread.technomagi.knowledge.technology.KnowledgeAugmentation;
import com.ollieread.technomagi.knowledge.technology.KnowledgeCyberlingualism;
import com.ollieread.technomagi.knowledge.technology.KnowledgeEfficancy;
import com.ollieread.technomagi.knowledge.technology.KnowledgePhotovoltaic;
import com.ollieread.technomagi.knowledge.technology.KnowledgePossession;
import com.ollieread.technomagi.knowledge.technology.KnowledgePower;
import com.ollieread.technomagi.knowledge.technology.KnowledgeScanning;
import com.ollieread.technomagi.knowledge.technology.KnowledgeTechnology;

public class Knowledge
{

    public static IKnowledge general;

    public static IKnowledge technology;
    public static IKnowledge power;
    public static IKnowledge augmentation;
    public static IKnowledge cyberlingualism;
    public static IKnowledge efficancy;
    public static IKnowledge possession;
    public static IKnowledge scanning;
    public static IKnowledge photovoltaic;

    public static IKnowledge cybermind;
    public static IKnowledge brainManipulation;
    public static IKnowledge computerInteraction;
    public static IKnowledge dataManipulation;
    public static IKnowledge electrocommunication;
    public static IKnowledge hud;
    public static IKnowledge mindshifting;
    public static IKnowledge naniteManipulation;
    public static IKnowledge navigation;
    public static IKnowledge perception;

    public static IKnowledge amplification;
    public static IKnowledge casting;
    public static IKnowledge negation;

    public static IKnowledge life;
    public static IKnowledge reproduction;
    public static IKnowledge passive;
    public static IKnowledge aggressive;
    public static IKnowledge flora;
    public static IKnowledge metabolism;

    public static IKnowledge coal;
    public static IKnowledge gold;
    public static IKnowledge iron;
    public static IKnowledge redstone;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering knowledge");

        // General
        general = new KnowledgeGeneral("general", new String[] {});

        // Resources
        coal = new KnowledgeCoal("coal", new String[] {});
        gold = new KnowledgeGold("gold", new String[] {});
        iron = new KnowledgeIron("iron", new String[] {});
        redstone = new KnowledgeRedstone("redstone", new String[] {});

        // Life
        life = new KnowledgeLife("life", new String[] {});
        reproduction = new KnowledgeReproduction("reproduction", new String[] {});
        passive = new KnowledgePassive("passive", new String[] { life.getName() });
        aggressive = new KnowledgeAggressive("aggressive", new String[] { life.getName() });
        flora = new KnowledgeFlora("flora", new String[] { life.getName() });
        metabolism = new KnowledgeMetabolism("metabolism", new String[] { life.getName() });

        // Technology
        technology = new KnowledgeTechnology("technology", new String[] {});
        power = new KnowledgePower("power", new String[] { coal.getName(), iron.getName(), gold.getName(), redstone.getName() });
        augmentation = new KnowledgeAugmentation("augmentation", new String[] {});
        cyberlingualism = new KnowledgeCyberlingualism("cyberlingualism", new String[] {});
        efficancy = new KnowledgeEfficancy("efficancy", new String[] {});
        possession = new KnowledgePossession("possession", new String[] {});
        scanning = new KnowledgeScanning("scanning", new String[] {});
        photovoltaic = new KnowledgePhotovoltaic("photovoltaic", new String[] { flora.getName(), power.getName() });

        // Cybermind
        cybermind = new KnowledgeCybermind("cybermind", new String[] {});
        brainManipulation = new KnowledgeBrainManipulation("brainManipulation", new String[] {});
        computerInteraction = new KnowledgeComputerInteraction("computerInteraction", new String[] {});
        dataManipulation = new KnowledgeDataManipulation("dataManipulation", new String[] { cybermind.getName() });
        electrocommunication = new KnowledgeElectrocommunication("electrocommunication", new String[] {});
        hud = new KnowledgeHUD("hud", new String[] {});
        mindshifting = new KnowledgeMindshifting("mindshifting", new String[] {});
        naniteManipulation = new KnowledgeNaniteManipulation("naniteManipulation", new String[] {});
        navigation = new KnowledgeNavigation("navigation", new String[] {});
        perception = new KnowledgePerception("perception", new String[] {});

        // Casting
        casting = new KnowledgeTechnology("casting", new String[] {});
        amplification = new KnowledgeAmplification("amplification", new String[] {});
        negation = new KnowledgeNegation("negation", new String[] {});
    }
}
