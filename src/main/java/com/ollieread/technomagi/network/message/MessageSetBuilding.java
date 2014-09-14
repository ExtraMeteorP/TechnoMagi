package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;

import com.ollieread.technomagi.tileentity.TileEntityConstruct;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSetBuilding implements IMessage, IMessageHandler<MessageSetBuilding, IMessage>
{
    public int x;
    public int y;
    public int z;
    public boolean isBuilding;

    public MessageSetBuilding()
    {
    }

    public MessageSetBuilding(TileEntityConstruct tile, boolean building)
    {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
        isBuilding = building;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        isBuilding = buffer.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeBoolean(isBuilding);
    }

    @Override
    public IMessage onMessage(MessageSetBuilding message, MessageContext ctx)
    {
        TileEntityConstruct tile = (TileEntityConstruct) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);

        if (tile != null) {
            tile.setBuilding(message.isBuilding);
        }

        return null;
    }
}
