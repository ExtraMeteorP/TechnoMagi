package com.ollieread.technomagi.event.handler;

import com.ollieread.technomagi.world.region.RegionWorldData;

import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldEventHandler
{

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        if (!event.world.isRemote) {
            RegionWorldData.get(event.world);
        }
    }

}
