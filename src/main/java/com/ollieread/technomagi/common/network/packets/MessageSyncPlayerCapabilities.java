package com.ollieread.technomagi.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.Technomagi;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncPlayerCapabilities implements IMessage
{
    public PlayerCapabilities capabilities;

    public MessageSyncPlayerCapabilities()
    {
    }

    public MessageSyncPlayerCapabilities(PlayerCapabilities capabilities)
    {
        this.capabilities = capabilities;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        capabilities = new PlayerCapabilities();
        capabilities.readCapabilitiesFromNBT(ByteBufUtils.readTag(buffer));
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        NBTTagCompound compound = new NBTTagCompound();
        capabilities.writeCapabilitiesToNBT(compound);
        ByteBufUtils.writeTag(buffer, compound);
    }

    public static class Handler implements IMessageHandler<MessageSyncPlayerCapabilities, IMessage>
    {
        @Override
        public IMessage onMessage(MessageSyncPlayerCapabilities message, MessageContext ctx)
        {
            if (Technomagi.proxy.isClient()) {
                EntityPlayer player = Technomagi.proxy.getClientPlayer();
                player.capabilities = message.capabilities;
            }

            return null;
        }
    }
}
