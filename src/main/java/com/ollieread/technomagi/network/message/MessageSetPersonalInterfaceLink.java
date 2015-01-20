package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.item.ItemPersonalInterface;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSetPersonalInterfaceLink implements IMessage, IMessageHandler<MessageSetPersonalInterfaceLink, IMessage>
{
    public String link;
    public int slot;

    public MessageSetPersonalInterfaceLink()
    {
    }

    public MessageSetPersonalInterfaceLink(String link)
    {
        this.link = link;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        link = ByteBufUtils.readUTF8String(buffer);
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        ByteBufUtils.writeUTF8String(buffer, link);
    }

    @Override
    public IMessage onMessage(MessageSetPersonalInterfaceLink message, MessageContext ctx)
    {
        try {
            EntityPlayer player = ctx.getServerHandler().playerEntity;

            if (player.getCurrentEquippedItem().getItem() instanceof ItemPersonalInterface) {
                ItemPersonalInterface.setLink(player.getCurrentEquippedItem(), message.link);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }
}
