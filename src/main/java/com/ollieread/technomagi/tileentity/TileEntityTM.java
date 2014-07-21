package com.ollieread.technomagi.tileentity;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageSyncTileEntityTM;

public class TileEntityTM extends TileEntity
{

    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageSyncTileEntityTM(this));
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.func_148857_g());
    }
}
