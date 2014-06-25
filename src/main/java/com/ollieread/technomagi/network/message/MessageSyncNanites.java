package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.extended.ExtendedNanites;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncNanites implements IMessage, IMessageHandler<MessageSyncNanites, IMessage>
{
    public int nanites = 0;
    public int data = 0;
    public Entity entity = null;

    public MessageSyncNanites()
    {
    }

    public MessageSyncNanites(int nanites, int data)
    {
        this.nanites = nanites;
        this.data = data;
    }

    public MessageSyncNanites(int nanites, int data, Entity entity)
    {
        this.nanites = nanites;
        this.data = data;
        this.entity = entity;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        this.nanites = buffer.readInt();
        this.data = buffer.readInt();

        if (buffer.isReadable()) {
            EntityPlayer player = TechnoMagi.proxy.getClientPlayer();

            this.entity = player.worldObj.getEntityByID(buffer.readInt());
        }
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(this.nanites);
        buffer.writeInt(this.data);

        if (this.entity != null) {
            buffer.writeInt(this.entity.getEntityId());
        }
    }

    @Override
    public IMessage onMessage(MessageSyncNanites message, MessageContext ctx)
    {
        EntityPlayer player = TechnoMagi.proxy.getClientPlayer();

        if (player != null) {
            ExtendedNanites n;

            if (message.entity != null) {
                n = ExtendedNanites.get(message.entity);
            } else {
                n = ExtendedNanites.get(player);
            }

            n.setNanites(message.nanites);
            n.setData(message.data);
        }

        return null;
    }
}
