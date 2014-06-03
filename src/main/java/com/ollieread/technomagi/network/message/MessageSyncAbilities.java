package com.ollieread.technomagi.network.message;

import com.ollieread.technomagi.player.PlayerAbilities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
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
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        PlayerAbilities.get(player).loadNBTData(message.data);

        return null;
    }
}
