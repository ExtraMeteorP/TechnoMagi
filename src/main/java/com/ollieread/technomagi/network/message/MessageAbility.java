package com.ollieread.technomagi.network.message;

import com.ollieread.technomagi.player.PlayerKnowledge;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageAbility implements IMessage, IMessageHandler<MessageAbility, IMessage>
{
	public int mode = 0;

    public MessageAbility()
    {
    }

    public MessageAbility(int mode)
    {
    	this.mode = mode;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
    	this.mode = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
    	buffer.writeInt(this.mode);
    }

    @Override
    public IMessage onMessage(MessageAbility message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        PlayerKnowledge knowledge = PlayerKnowledge.get(player);
        
        if(message.mode == 1) {
        	knowledge.abilities.setNextAbility();
        } else if(message.mode == 2) {
        	knowledge.abilities.setPreviousAbility();
        } else if(message.mode == 3) {
        	knowledge.abilities.useAbility();
        }

        return null;
    }
}
