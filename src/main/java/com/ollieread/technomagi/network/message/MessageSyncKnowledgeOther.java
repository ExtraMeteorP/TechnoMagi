package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncKnowledgeOther implements IMessage, IMessageHandler<MessageSyncKnowledgeOther, IMessage>
{
    public NBTTagCompound data;
    public String clientPlayer;

    public MessageSyncKnowledgeOther()
    {
    }

    public MessageSyncKnowledgeOther(EntityPlayer player)
    {
        data = new NBTTagCompound();
        clientPlayer = player.getCommandSenderName();
        PlayerKnowledge.get(player).saveNBTData(data);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        data = ByteBufUtils.readTag(buffer);
        clientPlayer = ByteBufUtils.readUTF8String(buffer);
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        ByteBufUtils.writeTag(buffer, data);
        ByteBufUtils.writeUTF8String(buffer, clientPlayer);
    }

    @Override
    public IMessage onMessage(MessageSyncKnowledgeOther message, MessageContext ctx)
    {
        EntityPlayer player = TechnoMagi.proxy.getClientPlayer();

        if (player != null) {
            EntityPlayer other = player.worldObj.getPlayerEntityByName(message.clientPlayer);
            PlayerKnowledge.get(other).loadNBTData(message.data);
        }

        return null;
    }
}
