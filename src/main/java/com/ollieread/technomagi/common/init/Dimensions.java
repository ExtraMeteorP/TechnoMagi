package com.ollieread.technomagi.common.init;

import net.minecraftforge.common.DimensionManager;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.world.WorldProviderPerception;
import com.ollieread.technomagi.world.region.PerceptionManager;

public class Dimensions
{

    public static int[] perceptionDimIDs;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering dimensions");

        if (Config.perceptionEnabled) {
            int[] dimensions = Config.perceptionDimensions;
            perceptionDimIDs = new int[dimensions.length];

            for (int i = 0; i < dimensions.length; i++) {
                int id = DimensionManager.getNextFreeDimId();
                perceptionDimIDs[i] = id;

                DimensionManager.registerProviderType(id, WorldProviderPerception.class, true);
                DimensionManager.registerDimension(id, id);
                PerceptionManager.addDimension(dimensions[i], id);
            }
        }
    }

}
