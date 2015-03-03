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

public class MessageSyncPlayerNanites implements IMessage
{
    public NBTTagCompound data;

    public MessageSyncPlayerNanites()
    {
    }

    public MessageSyncPlayerNanites(EntityPlayer player)
    {
        this(player, PlayerTechnomagi.get(player));
    }

    public MessageSyncPlayerNanites(EntityPlayer player, PlayerTechnomagi technomage)
    {
        data = new NBTTagCompound();
        technomage.nanites().saveNBTData(data);
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

    public static class Handler implements IMessageHandler<MessageSyncPlayerNanites, IMessage>
    {
        @Override
        public IMessage onMessage(MessageSyncPlayerNanites message, MessageContext ctx)
        {
            try {
                EntityPlayer player = Technomagi.proxy.getClientPlayer();
                PlayerTechnomagi technomage = PlayerTechnomagi.get(player);
                technomage.nanites().loadNBTData(message.data);
                GuiTechnomagi.updateContent();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
