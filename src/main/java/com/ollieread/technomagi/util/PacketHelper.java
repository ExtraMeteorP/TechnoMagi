package com.ollieread.technomagi.util;

import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageAnalyse;
import com.ollieread.technomagi.tileentity.TileEntityAnalysis;

public class PacketHelper
{

    public static void analysePacket(TileEntityAnalysis analysis)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageAnalyse(analysis));
    }

}
