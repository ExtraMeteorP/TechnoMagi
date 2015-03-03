package com.ollieread.technomagi.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.client.gui.GuiTechnomagi;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncPlayerKnowledge implements IMessage
{
    public NBTTagCompound data;

    public MessageSyncPlayerKnowledge()
    {
    }

    public MessageSyncPlayerKnowledge(EntityPlayer player)
    {
        this(player, PlayerTechnomagi.get(player));
    }

    public MessageSyncPlayerKnowledge(EntityPlayer player, PlayerTechnomagi technomage)
    {
        data = new NBTTagCompound();
        technomage.knowledge().saveNBTData(data);
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

    public static class Handler implements IMessageHandler<MessageSyncPlayerKnowledge, IMessage>
    {
        @Override
        public IMessage onMessage(MessageSyncPlayerKnowledge message, MessageContext ctx)
        {
            try {
                EntityPlayer player = Technomagi.proxy.getClientPlayer();
                PlayerTechnomagi technomage = PlayerTechnomagi.get(player);
                technomage.knowledge().loadNBTData(message.data);
                GuiTechnomagi.updateContent();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
