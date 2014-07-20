package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;

import com.ollieread.technomagi.tileentity.TileEntityAnalysis;
import com.ollieread.technomagi.tileentity.TileEntityTM;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageAnalyse implements IMessage, IMessageHandler<MessageAnalyse, IMessage>
{
    public int x;
    public int y;
    public int z;

    public MessageAnalyse()
    {
    }

    public MessageAnalyse(TileEntityTM tile)
    {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageAnalyse message, MessageContext ctx)
    {
        TileEntityAnalysis tile = (TileEntityAnalysis) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);

        if (tile != null) {
            tile.setInProgress(true);
        }

        return null;
    }
}
