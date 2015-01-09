package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.TechnoMagi;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncPlayerCapabilities implements IMessage, IMessageHandler<MessageSyncPlayerCapabilities, IMessage>
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

    @Override
    public IMessage onMessage(MessageSyncPlayerCapabilities message, MessageContext ctx)
    {
        if (TechnoMagi.proxy.isClient()) {
            EntityPlayer player = TechnoMagi.proxy.getClientPlayer();
            player.capabilities = message.capabilities;
        }

        return null;
    }
}
