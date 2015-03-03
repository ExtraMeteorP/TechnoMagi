package com.ollieread.technomagi.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.ollieread.technomagi.common.block.tile.TileBase;
import com.ollieread.technomagi.common.network.PacketHandler;
import com.ollieread.technomagi.common.network.packets.MessageChangeAbility;
import com.ollieread.technomagi.common.network.packets.MessageSpecialisation;
import com.ollieread.technomagi.common.network.packets.MessageSyncPlayerCapabilities;
import com.ollieread.technomagi.common.network.packets.MessageSyncTile;

public class PacketHelper
{

    public static void syncTile(TileBase tile)
    {
        PacketHandler.INSTANCE.sendToAll(new MessageSyncTile(tile));
    }

    public static void syncPlayerCapabilities(EntityPlayer player)
    {
        PacketHandler.INSTANCE.sendTo(new MessageSyncPlayerCapabilities(player.capabilities), (EntityPlayerMP) player);
    }

    public static void specialise(String specialisation)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageSpecialisation(specialisation));
    }

    public static void changeAbility(int direction)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageChangeAbility(direction));
    }

}
