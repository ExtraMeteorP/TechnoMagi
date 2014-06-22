package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncNanites implements IMessage, IMessageHandler<MessageSyncNanites, IMessage>
{
    public int nanites = 0;

    public MessageSyncNanites()
    {
    }

    public MessageSyncNanites(int nanites)
    {
        this.nanites = nanites;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        this.nanites = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(this.nanites);
    }

    @Override
    public IMessage onMessage(MessageSyncNanites message, MessageContext ctx)
    {
        EntityPlayer player = TechnoMagi.proxy.getClientPlayer();

        if (player != null) {
            PlayerKnowledge knowledge = PlayerKnowledge.get(player);
            knowledge.setNanites(message.nanites);
        }

        return null;
    }
}
