package com.ollieread.technomagi.network.message;

import io.netty.buffer.ByteBuf;

import com.ollieread.technomagi.tileentity.TileEntityMachineAssembler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSetCrafting implements IMessage, IMessageHandler<MessageSetCrafting, IMessage>
{
    public int x;
    public int y;
    public int z;
    public boolean isCrafting;

    public MessageSetCrafting()
    {
    }

    public MessageSetCrafting(TileEntityMachineAssembler tile, boolean crafting)
    {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
        isCrafting = crafting;
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        isCrafting = buffer.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeBoolean(isCrafting);
    }

    @Override
    public IMessage onMessage(MessageSetCrafting message, MessageContext ctx)
    {
        TileEntityMachineAssembler tile = (TileEntityMachineAssembler) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);

        if (tile != null) {
            tile.setCrafting(true);
        }

        return null;
    }
}
