package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessagePlayerInteractEvent implements IMessage, IMessageHandler<MessagePlayerInteractEvent, IMessage>
{

    public int action;
    public int x;
    public int y;
    public int z;
    public int face;

    public MessagePlayerInteractEvent()
    {
    }

    public MessagePlayerInteractEvent(PlayerInteractEvent event)
    {
        action = event.action.ordinal();
        x = event.x;
        y = event.y;
        z = event.z;
        face = event.face;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        action = buffer.readInt();
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        face = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(action);
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeInt(face);
    }

    @Override
    public IMessage onMessage(MessagePlayerInteractEvent message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        ExtendedPlayerKnowledge knowledge = ExtendedPlayerKnowledge.get(player);

        PlayerInteractEvent event = new PlayerInteractEvent(player, PlayerInteractEvent.Action.values()[message.action], message.x, message.y, message.z, message.face, player.worldObj);
        // CommonProxy.playerEventHandler.onPlayerInteract(event);

        return null;
    }
}
