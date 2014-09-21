package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.tileentity.TileEntityArchive;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSetArchive implements IMessage, IMessageHandler<MessageSetArchive, IMessage>
{
    public int x;
    public int y;
    public int z;
    public int type;
    public int subtype;
    public int page;

    public MessageSetArchive()
    {
    }

    public MessageSetArchive(TileEntityArchive tile, int type, int subtype, int page)
    {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;

        this.type = type;
        this.subtype = subtype;
        this.page = page;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        type = buffer.readInt();
        subtype = buffer.readInt();
        page = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeInt(type);
        buffer.writeInt(subtype);
        buffer.writeInt(page);
    }

    @Override
    public IMessage onMessage(MessageSetArchive message, MessageContext ctx)
    {
        TileEntityArchive tile = (TileEntityArchive) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);

        if (tile != null) {
            tile.setSubType(message.subtype);
            tile.setPage(message.page);

            if (tile.getType() != message.type) {
                tile.setType(message.type);
                ctx.getServerHandler().playerEntity.openGui(TechnoMagi.instance, CommonProxy.GUI_ARCHIVE, ctx.getServerHandler().playerEntity.worldObj, message.x, message.y, message.z);
            }
        }

        return null;
    }
}
