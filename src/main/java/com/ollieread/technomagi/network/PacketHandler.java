package com.ollieread.technomagi.network;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.network.message.*;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID.toLowerCase());

    public static void init()
    {
    	INSTANCE.registerMessage(MessageSyncKnowledge.class, MessageSyncKnowledge.class, 0, Side.CLIENT);
    	INSTANCE.registerMessage(MessageSyncAbilities.class, MessageSyncAbilities.class, 1, Side.CLIENT);
    	INSTANCE.registerMessage(MessageSpecialisation.class, MessageSpecialisation.class, 2, Side.SERVER);
    	INSTANCE.registerMessage(MessageAbility.class, MessageAbility.class, 3, Side.SERVER);
    }
}
