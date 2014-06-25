package com.ollieread.technomagi.tileentity;

import net.minecraft.network.Packet;
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
}
