package com.ollieread.technomagi.common.network;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.network.packets.MessageChangeAbility;
import com.ollieread.technomagi.common.network.packets.MessageReleaseCasting;
import com.ollieread.technomagi.common.network.packets.MessageSpecialisation;
import com.ollieread.technomagi.common.network.packets.MessageSyncEntity;
import com.ollieread.technomagi.common.network.packets.MessageSyncPlayer;
import com.ollieread.technomagi.common.network.packets.MessageSyncPlayerAbilities;
import com.ollieread.technomagi.common.network.packets.MessageSyncPlayerCapabilities;
import com.ollieread.technomagi.common.network.packets.MessageSyncPlayerKnowledge;
import com.ollieread.technomagi.common.network.packets.MessageSyncPlayerNanites;
import com.ollieread.technomagi.common.network.packets.MessageSyncTile;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Technomagi.MODID.toLowerCase());

    public static void init()
    {
        INSTANCE.registerMessage(MessageSyncTile.Handler.class, MessageSyncTile.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(MessageSyncPlayerCapabilities.Handler.class, MessageSyncPlayerCapabilities.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MessageSyncPlayer.Handler.class, MessageSyncPlayer.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(MessageSyncPlayerAbilities.Handler.class, MessageSyncPlayerAbilities.class, 3, Side.CLIENT);
        INSTANCE.registerMessage(MessageSyncPlayerNanites.Handler.class, MessageSyncPlayerNanites.class, 4, Side.CLIENT);
        INSTANCE.registerMessage(MessageSyncPlayerKnowledge.Handler.class, MessageSyncPlayerKnowledge.class, 5, Side.CLIENT);
        INSTANCE.registerMessage(MessageSyncEntity.Handler.class, MessageSyncEntity.class, 6, Side.CLIENT);
        INSTANCE.registerMessage(MessageSpecialisation.Handler.class, MessageSpecialisation.class, 7, Side.SERVER);
        INSTANCE.registerMessage(MessageChangeAbility.Handler.class, MessageChangeAbility.class, 8, Side.SERVER);
        INSTANCE.registerMessage(MessageReleaseCasting.Handler.class, MessageReleaseCasting.class, 9, Side.SERVER);
    }
}
