package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageEntityInteractEvent implements IMessage, IMessageHandler<MessageEntityInteractEvent, IMessage>
{

    public int target;

    public MessageEntityInteractEvent()
    {
    }

    public MessageEntityInteractEvent(EntityInteractEvent event)
    {
        target = event.target.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        target = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(target);
    }

    @Override
    public IMessage onMessage(MessageEntityInteractEvent message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        ExtendedPlayerKnowledge knowledge = ExtendedPlayerKnowledge.get(player);
        Entity entity = player.worldObj.getEntityByID(message.target);

        EntityInteractEvent event = new EntityInteractEvent(player, entity);
        // CommonProxy.playerEventHandler.onEntityInteract(event);

        return null;
    }
}
