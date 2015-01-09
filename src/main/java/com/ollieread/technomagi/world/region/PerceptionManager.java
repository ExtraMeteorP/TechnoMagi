package com.ollieread.technomagi.world.region;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.init.Dimensions;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageSyncPerceptionFilters;

public class PerceptionManager
{

    public static Map<Integer, Integer> dimensionFilters = new HashMap<Integer, Integer>();

    public static void addDimension(int dimension, int perception)
    {
        TechnoMagi.logger.log(Level.INFO, "Adding perception filter manager for " + dimension);

        dimensionFilters.put(dimension, perception);
    }

    public static int getForDimension(int dimension)
    {
        return dimensionFilters.get(dimension);
    }

    public static void sync(int dimension, EntityPlayer player)
    {
        if (!player.worldObj.isRemote) {
            int perception = getForDimension(dimension);
            PacketHandler.INSTANCE.sendToAll(new MessageSyncPerceptionFilters(dimension, perception));
        }
    }

    public static boolean notPerceptionDimension(int dimension)
    {
        for (int i = 0; i < Dimensions.perceptionDimIDs.length; i++) {
            if (dimension == Dimensions.perceptionDimIDs[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean hasPerceptionDimension(int dimension)
    {
        return dimensionFilters.containsKey(dimension);
    }

}
