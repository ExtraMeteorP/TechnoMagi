package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncKnowledge implements IMessage, IMessageHandler<MessageSyncKnowledge, IMessage>
{
    public NBTTagCompound data;

    public MessageSyncKnowledge()
    {
    }

    public MessageSyncKnowledge(EntityPlayer player)
    {
        data = new NBTTagCompound();
        ExtendedPlayerKnowledge.get(player).saveNBTData(data);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        data = ByteBufUtils.readTag(buffer);
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        ByteBufUtils.writeTag(buffer, data);
    }

    @Override
    public IMessage onMessage(MessageSyncKnowledge message, MessageContext ctx)
    {
        EntityPlayer player = TechnoMagi.proxy.getClientPlayer();

        if (player != null) {
            ExtendedPlayerKnowledge.get(player).loadNBTData(message.data);
        }

        return null;
    }
}
