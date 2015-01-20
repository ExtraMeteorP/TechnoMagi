package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.item.ItemPersonalInterface;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSetPersonalInterfaceSyncing implements IMessage, IMessageHandler<MessageSetPersonalInterfaceSyncing, IMessage>
{
    public String link;
    public int slot;

    public MessageSetPersonalInterfaceSyncing()
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

    @Override
    public IMessage onMessage(MessageSetPersonalInterfaceSyncing message, MessageContext ctx)
    {
        try {
            EntityPlayer player = ctx.getServerHandler().playerEntity;

            if (player.getCurrentEquippedItem().getItem() instanceof ItemPersonalInterface) {
                boolean syncing = ItemPersonalInterface.getSyncing(player.getCurrentEquippedItem());
                ItemPersonalInterface.setSyncing(player.getCurrentEquippedItem(), !syncing);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }
}
