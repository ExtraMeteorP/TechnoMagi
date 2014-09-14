package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.tileentity.TileEntityTM;
import com.ollieread.technomagi.tileentity.TileEntityTeleporter;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenTeleporter implements IMessage, IMessageHandler<MessageOpenTeleporter, IMessage>
{
    public NBTTagCompound data;
    public int x;
    public int y;
    public int z;

    public MessageOpenTeleporter()
    {
    }

    public MessageOpenTeleporter(int x, int y, int z)
    {
        data = new NBTTagCompound();

        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        data = ByteBufUtils.readTag(buffer);
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        ByteBufUtils.writeTag(buffer, data);
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageOpenTeleporter message, MessageContext ctx)
    {
        TileEntityTM tile = (TileEntityTM) FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tile != null && tile instanceof TileEntityTeleporter) {
            EntityPlayer player = TechnoMagi.proxy.getClientPlayer();

            if (player != null) {
                player.openGui(TechnoMagi.instance, CommonProxy.GUI_TELEPORTER, player.worldObj, message.x, message.y, message.z);
            }
        }

        return null;
    }
}
