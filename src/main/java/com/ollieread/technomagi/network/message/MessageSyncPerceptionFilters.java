package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;

import com.ollieread.technomagi.world.region.PerceptionManager;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncPerceptionFilters implements IMessage, IMessageHandler<MessageSyncPerceptionFilters, IMessage>
{
    public int dimension;
    public int perception;

    public MessageSyncPerceptionFilters()
    {
    }

    public MessageSyncPerceptionFilters(int dimension, int perception)
    {
        this.dimension = dimension;
        this.perception = perception;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        dimension = buffer.readInt();
        perception = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(dimension);
        buffer.writeInt(perception);
    }

    @Override
    public IMessage onMessage(MessageSyncPerceptionFilters message, MessageContext ctx)
    {
        PerceptionManager.addDimension(message.dimension, message.perception);

        return null;
    }
}
