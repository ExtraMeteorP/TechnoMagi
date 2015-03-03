package com.ollieread.technomagi.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.entity.PlayerTechnomagi;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageChangeAbility implements IMessage
{
    public int direction;

    public MessageChangeAbility()
    {
    }

    public MessageChangeAbility(int direction)
    {
        this.direction = direction;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        direction = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(direction);
    }

    public static class Handler implements IMessageHandler<MessageChangeAbility, IMessage>
    {
        @Override
        public IMessage onMessage(MessageChangeAbility message, MessageContext ctx)
        {
            try {
                EntityPlayer player = ctx.getServerHandler().playerEntity;
                PlayerTechnomagi technomage = PlayerTechnomagi.get(player);

                if (message.direction == 1) {
                    technomage.abilities().setNextAbility();
                } else {
                    technomage.abilities().setPreviousAbility();
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
