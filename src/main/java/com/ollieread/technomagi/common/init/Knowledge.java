package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.research.KnowledgeDensity;
import com.ollieread.technomagi.research.KnowledgeForceI;
import com.ollieread.technomagi.research.KnowledgeForceII;
import com.ollieread.technomagi.research.KnowledgeHarvestI;
import com.ollieread.technomagi.research.KnowledgeHarvestII;
import com.ollieread.technomagi.research.KnowledgeHarvestIII;
import com.ollieread.technomagi.research.KnowledgeLightManipulationI;
import com.ollieread.technomagi.research.KnowledgeLightManipulationII;
import com.ollieread.technomagi.research.KnowledgeMotion;
import com.ollieread.technomagi.research.KnowledgePyrologyI;
import com.ollieread.technomagi.research.KnowledgePyrologyII;
import com.ollieread.technomagi.research.KnowledgeTeleportationI;
import com.ollieread.technomagi.research.KnowledgeTeleportationII;

public class Knowledge
{

    public static IKnowledge density;
    public static IKnowledge forceI;
    public static IKnowledge forceII;
    public static IKnowledge pyrologyI;
    public static IKnowledge pyrologyII;
    public static IKnowledge lightManipulationI;
    public static IKnowledge lightManipulationII;
    public static IKnowledge teleportationI;
    public static IKnowledge teleportationII;
    public static IKnowledge motionI;
    public static IKnowledge harvestI;
    public static IKnowledge harvestII;
    public static IKnowledge harvestIII;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering knowledge");

        density = new KnowledgeDensity("density");
        forceI = new KnowledgeForceI("forceI");
        forceII = new KnowledgeForceII("foreceII");
        pyrologyI = new KnowledgePyrologyI("pyrologyI");
        pyrologyII = new KnowledgePyrologyII("pyrologyII");
        lightManipulationI = new KnowledgeLightManipulationI("lightManipulationI");
        lightManipulationII = new KnowledgeLightManipulationII("lightManipulationII");
        teleportationI = new KnowledgeTeleportationI("teleportationI");
        teleportationII = new KnowledgeTeleportationII("teleportationII");
        motionI = new KnowledgeMotion("motionI");
        harvestI = new KnowledgeHarvestI("harvestI");
        harvestII = new KnowledgeHarvestII("harvestII");
        harvestIII = new KnowledgeHarvestIII("harvestIII");
    }
}
