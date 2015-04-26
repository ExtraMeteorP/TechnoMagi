package com.ollieread.technomagi.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageReleaseCasting implements IMessage
{

    public MessageReleaseCasting()
    {
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
    }

    public static class Handler implements IMessageHandler<MessageReleaseCasting, IMessage>
    {
        @Override
        public IMessage onMessage(MessageReleaseCasting message, MessageContext ctx)
        {
            try {
                EntityPlayer player = ctx.getServerHandler().playerEntity;

                PlayerHelper.getAbilities(player).releaseCasting();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
