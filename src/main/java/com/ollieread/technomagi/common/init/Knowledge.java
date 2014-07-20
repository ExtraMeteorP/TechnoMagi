package com.ollieread.technomagi.common.init;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.research.KnowledgeLightManipulationI;
import com.ollieread.technomagi.research.KnowledgeLightManipulationII;
import com.ollieread.technomagi.research.KnowledgeMotion;
import com.ollieread.technomagi.research.KnowledgeTeleportationI;
import com.ollieread.technomagi.research.KnowledgeTeleportationII;
import com.ollieread.technomagi.research.analysis.ResearchAnalysisSpeedPotionI;
import com.ollieread.technomagi.research.analysis.ResearchAnalysisSpeedPotionII;

public class Knowledge
{

    public static IKnowledge lightManipulationI;
    public static IKnowledge lightManipulationII;
    public static IKnowledge teleportationI;
    public static IKnowledge teleportationII;
    public static IKnowledge motion;

    public static IResearchAnalysis speedPotionI;
    public static IResearchAnalysis speedPotionII;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering knowledge");

        lightManipulationI = new KnowledgeLightManipulationI("lightManipulationI");
        lightManipulationII = new KnowledgeLightManipulationII("lightManipulationII");
        teleportationI = new KnowledgeTeleportationI("teleportationI");
        teleportationII = new KnowledgeTeleportationII("teleportationII");
        motion = new KnowledgeMotion("motion");

        speedPotionI = new ResearchAnalysisSpeedPotionI("speedPotionI", motion.getName(), 15, 1, new ArrayList(Arrays.asList(new ItemStack(net.minecraft.init.Items.potionitem, 1, 8194))));
        speedPotionII = new ResearchAnalysisSpeedPotionII("speedPotionII", motion.getName(), 15, 1, new ArrayList(Arrays.asList(new ItemStack(net.minecraft.init.Items.potionitem, 1, 8226))));
    }
}
