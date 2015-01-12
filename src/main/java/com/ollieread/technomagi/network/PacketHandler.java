package com.ollieread.technomagi.network;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.network.message.MessageEntityInteractEvent;
import com.ollieread.technomagi.network.message.MessageOpenGUI;
import com.ollieread.technomagi.network.message.MessageOpenTeleporter;
import com.ollieread.technomagi.network.message.MessagePlayerInteractEvent;
import com.ollieread.technomagi.network.message.MessageSetArchive;
import com.ollieread.technomagi.network.message.MessageSetBuilding;
import com.ollieread.technomagi.network.message.MessageSetCrafting;
import com.ollieread.technomagi.network.message.MessageSetProgress;
import com.ollieread.technomagi.network.message.MessageSetTeleporterMode;
import com.ollieread.technomagi.network.message.MessageSyncPerceptionFilters;
import com.ollieread.technomagi.network.message.MessageSyncPlayerCapabilities;
import com.ollieread.technomagi.network.message.MessageSyncRegions;
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
        INSTANCE.registerMessage(MessageSetCrafting.class, MessageSetCrafting.class, 4, Side.SERVER);
        INSTANCE.registerMessage(MessageSetBuilding.class, MessageSetBuilding.class, 5, Side.SERVER);
        INSTANCE.registerMessage(MessageOpenTeleporter.class, MessageOpenTeleporter.class, 6, Side.CLIENT);
        INSTANCE.registerMessage(MessageSetTeleporterMode.class, MessageSetTeleporterMode.class, 7, Side.SERVER);
        INSTANCE.registerMessage(MessageSetArchive.class, MessageSetArchive.class, 8, Side.SERVER);
        INSTANCE.registerMessage(MessageSyncPerceptionFilters.class, MessageSyncPerceptionFilters.class, 9, Side.CLIENT);
        INSTANCE.registerMessage(MessageSyncRegions.class, MessageSyncRegions.class, 10, Side.CLIENT);
        INSTANCE.registerMessage(MessageSyncPlayerCapabilities.class, MessageSyncPlayerCapabilities.class, 11, Side.CLIENT);
        INSTANCE.registerMessage(MessageOpenGUI.class, MessageOpenGUI.class, 12, Side.SERVER);
    }
}
