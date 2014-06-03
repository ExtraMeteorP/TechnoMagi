package com.ollieread.technomagi.network.message;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.player.PlayerKnowledge;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSpecialisation implements IMessage, IMessageHandler<MessageSpecialisation, IMessage>
{
    public int specialisation;

    public MessageSpecialisation()
    {
    }

    public MessageSpecialisation(int specialisation)
    {
        this.specialisation = specialisation;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        this.specialisation = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(this.specialisation);
    }

    @Override
    public IMessage onMessage(MessageSpecialisation message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        PlayerKnowledge.get(player).setSpecialisation(TMRegistry.getSpecialisationName(message.specialisation));

        return null;
    }
}
