package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.player.PlayerAbilities;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncAbilities implements IMessage, IMessageHandler<MessageSyncAbilities, IMessage>
{
    public NBTTagCompound data;

    public MessageSyncAbilities()
    {
    }

    public MessageSyncAbilities(EntityPlayer player)
    {
        data = new NBTTagCompound();
        PlayerAbilities.get(player).saveNBTData(data);
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
    public IMessage onMessage(MessageSyncAbilities message, MessageContext ctx)
    {
        EntityPlayer player = TechnoMagi.proxy.getClientPlayer();

        if (player != null) {
            PlayerAbilities.get(player).loadNBTData(message.data);
        }

        return null;
    }
}
