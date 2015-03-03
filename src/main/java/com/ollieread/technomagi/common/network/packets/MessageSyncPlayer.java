package com.ollieread.technomagi.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncPlayer implements IMessage
{
    public NBTTagCompound data;

    public MessageSyncPlayer()
    {
    }

    public MessageSyncPlayer(EntityPlayer player)
    {
        this(player, PlayerTechnomagi.get(player));
    }

    public MessageSyncPlayer(EntityPlayer player, PlayerTechnomagi technomage)
    {
        data = new NBTTagCompound();
        technomage.saveNBTData(data);
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

    public static class Handler implements IMessageHandler<MessageSyncPlayer, IMessage>
    {
        @Override
        public IMessage onMessage(MessageSyncPlayer message, MessageContext ctx)
        {
            try {
                EntityPlayer player = Technomagi.proxy.getClientPlayer();
                PlayerTechnomagi technomage = PlayerTechnomagi.get(player);
                technomage.loadNBTData(message.data);
                Technomagi.proxy.updateOverlay();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
