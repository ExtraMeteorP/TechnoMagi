package com.ollieread.technomagi.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSpecialisation implements IMessage
{
    public String specialisation;

    public MessageSpecialisation()
    {
    }

    public MessageSpecialisation(String specialisation)
    {
        this.specialisation = specialisation;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        specialisation = ByteBufUtils.readUTF8String(buffer);
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        ByteBufUtils.writeUTF8String(buffer, this.specialisation);
    }

    public static class Handler implements IMessageHandler<MessageSpecialisation, IMessage>
    {
        @Override
        public IMessage onMessage(MessageSpecialisation message, MessageContext ctx)
        {
            try {
                EntityPlayer player = ctx.getServerHandler().playerEntity;

                PlayerHelper.specialise(player, message.specialisation);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
