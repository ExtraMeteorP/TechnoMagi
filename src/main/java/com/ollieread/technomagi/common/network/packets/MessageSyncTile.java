package com.ollieread.technomagi.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.blocks.tiles.TileBase;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncTile implements IMessage, IMessageHandler<MessageSyncTile, IMessage>
{
    public NBTTagCompound data;
    public int x;
    public int y;
    public int z;

    public MessageSyncTile()
    {
    }

    public MessageSyncTile(TileBase tile)
    {
        data = new NBTTagCompound();
        tile.writeToNBT(data);

        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
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
    public IMessage onMessage(MessageSyncTile message, MessageContext ctx)
    {
        try {
            TileEntity tile = Technomagi.proxy.getClientWorld().getTileEntity(message.x, message.y, message.z);

            if (tile == null) {
                System.out.println("TE vanished mid sync");
                System.out.println(message.x + ":" + message.y + ":" + message.z);
            }

            TileBase tileBasic = (TileBase) tile;

            if (tileBasic != null) {
                tileBasic.readFromNBT(message.data);

                /*
                 * if (tileBasic instanceof ITileEntityDisguisable) {
                 * tileBasic.getWorldObj
                 * ().markBlockRangeForRenderUpdate(message.x, message.y,
                 * message.z, message.x, message.y, message.z); }
                 */
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }
}
