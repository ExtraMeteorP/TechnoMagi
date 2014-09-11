package com.ollieread.technomagi.util;

import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageSetCrafting;
import com.ollieread.technomagi.network.message.MessageSetProgress;
import com.ollieread.technomagi.network.message.MessageSyncTileEntityTM;
import com.ollieread.technomagi.tileentity.TileEntityCrafting;
import com.ollieread.technomagi.tileentity.TileEntityResearch;
import com.ollieread.technomagi.tileentity.TileEntityTM;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketHelper
{

    public static void setCrafting(TileEntityCrafting machine, boolean progress)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageSetCrafting(machine, progress));
    }

    public static void setProgress(TileEntityResearch machine, boolean progress)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageSetProgress(machine, progress));
    }

    public static void syncTile(IMessage message)
    {
        PacketHandler.INSTANCE.sendToAll(message);
    }

    public static void syncTile(TileEntityTM tile)
    {
        syncTile(new MessageSyncTileEntityTM(tile));
    }

}
