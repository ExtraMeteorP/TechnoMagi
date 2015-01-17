package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;

import com.ollieread.technomagi.tileentity.abstracts.MachineResearch;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSetProgress implements IMessage, IMessageHandler<MessageSetProgress, IMessage>
{
    public int x;
    public int y;
    public int z;
    public boolean inProgress;

    public MessageSetProgress()
    {
    }

    public MessageSetProgress(MachineResearch tile, boolean progress)
    {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
        inProgress = progress;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        inProgress = buffer.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeBoolean(inProgress);
    }

    @Override
    public IMessage onMessage(MessageSetProgress message, MessageContext ctx)
    {
        MachineResearch tile = (MachineResearch) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);

        if (tile != null) {
            tile.setInProgress(message.inProgress);
        }

        return null;
    }
}
