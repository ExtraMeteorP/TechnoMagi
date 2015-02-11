package com.ollieread.technomagi.api.util;

import com.ollieread.technomagi.common.block.tile.TileBase;
import com.ollieread.technomagi.common.network.PacketHandler;
import com.ollieread.technomagi.common.network.packets.MessageSyncTile;

public class PacketHelper
{

    public static void syncTile(TileBase tile)
    {
        PacketHandler.INSTANCE.sendToAll(new MessageSyncTile(tile));
    }

}
