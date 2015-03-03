package com.ollieread.technomagi.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.entity.EntityTechnomagi;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncEntity implements IMessage
{
    public NBTTagCompound data;
    public int id;

    public MessageSyncEntity()
    {
    }

    public MessageSyncEntity(EntityLivingBase entity)
    {
        this(entity, EntityTechnomagi.get(entity));
    }

    public MessageSyncEntity(EntityLivingBase entity, EntityTechnomagi technomage)
    {
        this.data = new NBTTagCompound();
        this.id = entity.getEntityId();

        technomage.saveNBTData(data);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        this.data = ByteBufUtils.readTag(buffer);
        this.id = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        ByteBufUtils.writeTag(buffer, data);
        buffer.writeInt(id);
    }

    public static class Handler implements IMessageHandler<MessageSyncEntity, IMessage>
    {
        @Override
        public IMessage onMessage(MessageSyncEntity message, MessageContext ctx)
        {
            try {
                World world = Technomagi.proxy.getClientWorld();
                EntityLivingBase entity = (EntityLivingBase) world.getEntityByID(message.id);
                EntityTechnomagi technomage = EntityTechnomagi.get(entity);
                technomage.loadNBTData(message.data);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
