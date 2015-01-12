package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenGUI implements IMessage, IMessageHandler<MessageOpenGUI, IMessage>
{

    public int gui;

    public MessageOpenGUI()
    {
    }

    public MessageOpenGUI(int gui)
    {
        this.gui = gui;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        gui = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(gui);
    }

    @Override
    public IMessage onMessage(MessageOpenGUI message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if (player != null) {
            ExtendedPlayerKnowledge knowledge = PlayerHelper.getPlayerKnowledge(player);

            if (knowledge != null && !knowledge.canSpecialise()) {
                player.openGui(TechnoMagi.instance, message.gui, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
            }
        }

        return null;
    }
}
