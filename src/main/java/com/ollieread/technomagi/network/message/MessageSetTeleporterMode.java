package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;

import com.ollieread.technomagi.tileentity.TileEntityTeleporter;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSetTeleporterMode implements IMessage, IMessageHandler<MessageSetTeleporterMode, IMessage>
{
    public int x;
    public int y;
    public int z;
    public int mode;

    public MessageSetTeleporterMode()
    {
    }

    public MessageSetTeleporterMode(TileEntityTeleporter tile, int mode)
    {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
        this.mode = mode;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        mode = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeInt(mode);
    }

    @Override
    public IMessage onMessage(MessageSetTeleporterMode message, MessageContext ctx)
    {
        TileEntityTeleporter tile = (TileEntityTeleporter) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);

        if (tile != null) {
            tile.setMode(message.mode);
        }

        return null;
    }
}
