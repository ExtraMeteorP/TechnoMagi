package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.world.region.RegionManager;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncRegions implements IMessage, IMessageHandler<MessageSyncRegions, IMessage>
{
    public int dimension;
    public NBTTagCompound compound;

    public MessageSyncRegions()
    {
    }

    public MessageSyncRegions(int dimension, NBTTagCompound compound)
    {
        this.compound = compound;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        dimension = buffer.readInt();
        compound = ByteBufUtils.readTag(buffer);
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(dimension);
        ByteBufUtils.writeTag(buffer, compound);
    }

    @Override
    public IMessage onMessage(MessageSyncRegions message, MessageContext ctx)
    {
        if (TechnoMagi.proxy.isClient()) {
            RegionManager.getInstance(message.dimension).load(message.compound);
        }

        return null;
    }
}
