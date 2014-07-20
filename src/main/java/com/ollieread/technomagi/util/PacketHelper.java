package com.ollieread.technomagi.util;

import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageAnalyse;
import com.ollieread.technomagi.tileentity.TileEntityAnalysis;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketHelper
{

    public static void analysePacket(TileEntityAnalysis analysis)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageAnalyse(analysis));
    }

    public static void syncTile(IMessage message)
    {
        PacketHandler.INSTANCE.sendToAll(message);
    }

}
