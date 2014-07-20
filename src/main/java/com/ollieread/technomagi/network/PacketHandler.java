package com.ollieread.technomagi.network;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.network.message.MessageSetProgress;
import com.ollieread.technomagi.network.message.MessageEntityInteractEvent;
import com.ollieread.technomagi.network.message.MessagePlayerInteractEvent;
import com.ollieread.technomagi.network.message.MessageSyncTileEntityTM;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID.toLowerCase());

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering packets");

        INSTANCE.registerMessage(MessagePlayerInteractEvent.class, MessagePlayerInteractEvent.class, 0, Side.SERVER);
        INSTANCE.registerMessage(MessageEntityInteractEvent.class, MessageEntityInteractEvent.class, 1, Side.SERVER);
        INSTANCE.registerMessage(MessageSyncTileEntityTM.class, MessageSyncTileEntityTM.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(MessageSetProgress.class, MessageSetProgress.class, 3, Side.SERVER);
    }
}
