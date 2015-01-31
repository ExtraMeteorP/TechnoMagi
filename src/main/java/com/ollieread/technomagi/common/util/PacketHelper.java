package com.ollieread.technomagi.common.util;

import com.ollieread.technomagi.common.blocks.tiles.TileBase;
import com.ollieread.technomagi.common.network.PacketHandler;
import com.ollieread.technomagi.common.network.packets.MessageSyncTile;

public class PacketHelper
{

    public static void syncTile(TileBase tile)
    {
        PacketHandler.INSTANCE.sendToAll(new MessageSyncTile(tile));
    }

}
