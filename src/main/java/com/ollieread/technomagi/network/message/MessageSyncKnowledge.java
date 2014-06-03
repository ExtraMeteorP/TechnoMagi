package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;

import com.ollieread.technomagi.player.PlayerKnowledge;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
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
		PlayerKnowledge.get(player).saveNBTData(data);
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
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        PlayerKnowledge.get(player).loadNBTData(message.data);

        return null;
    }
}
