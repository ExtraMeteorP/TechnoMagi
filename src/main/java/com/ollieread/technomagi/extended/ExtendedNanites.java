package com.ollieread.technomagi.extended;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageSyncNanites;

public class ExtendedNanites implements IExtendedEntityProperties
{

    public static String PROP_NAME = "TechnoMageNanites";
    /**
     * The players nanites, default is 100.
     */
    private int nanites = 100;

    private int data = 0;

    private int maxNanites = 100;

    private int maxData = 100;

    private String owner = "none";

    protected Entity entity;

    public ExtendedNanites(Entity entity)
    {
        this.entity = entity;
    }

    public void init(Entity entity, World world)
    {
    }

    private boolean isPlayer()
    {
        return entity instanceof EntityPlayer;
    }

    public static final void register(Entity entity)
    {
        entity.registerExtendedProperties(PROP_NAME, new ExtendedNanites(entity));
    }

    public static final ExtendedNanites get(Entity entity)
    {
        return (ExtendedNanites) entity.getExtendedProperties(PROP_NAME);
    }

    public void sync()
    {
        EntityPlayer player;

        if (isPlayer()) {
            player = (EntityPlayerMP) entity;
        } else {
            player = (EntityPlayerMP) getOwnerPlayer();
        }

        if (player != null) {
            PacketHandler.INSTANCE.sendTo(new MessageSyncNanites(nanites, data), (EntityPlayerMP) entity);
        }
    }

    public void syncTo(EntityPlayer entityPlayer)
    {
        if (!entity.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendTo(new MessageSyncNanites(nanites, data), (EntityPlayerMP) entityPlayer);
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = new NBTTagCompound();

        properties.setInteger("Nanites", nanites);
        properties.setInteger("Data", data);
        properties.setString("Owner", owner);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag(PROP_NAME);

        nanites = properties.getInteger("Nanites");
        data = properties.getInteger("Data");
        owner = properties.getString("Owner");
    }

    public static void saveProxyData(EntityPlayer player)
    {
        ExtendedNanites playerData = ExtendedNanites.get(player);
        NBTTagCompound savedData = new NBTTagCompound();

        playerData.saveNBTData(savedData);

        CommonProxy.storeEntityData(getSaveKey(player), savedData);
    }

    public static void loadProxyData(EntityPlayer player)
    {
        ExtendedNanites playerData = ExtendedNanites.get(player);
        NBTTagCompound savedData = CommonProxy.getEntityData(getSaveKey(player));

        if (savedData != null) {
            playerData.loadNBTData(savedData);
        }

        playerData.sync();
    }

    protected static String getSaveKey(EntityPlayer player)
    {
        return player.getCommandSenderName() + ":" + PROP_NAME;
    }

    public boolean increaseNanites(int i)
    {
        if (nanites < maxNanites) {
            if ((nanites + i) > maxNanites) {
                nanites = maxNanites;
                sync();
                return true;
            } else {
                nanites += i;
                sync();
                return true;
            }
        }

        return false;
    }

    public boolean decreaseNanites(int i)
    {
        if (!isPlayer() || (isPlayer() && !((EntityPlayer) entity).capabilities.isCreativeMode)) {
            if (nanites > 0) {
                if ((nanites - i) >= 0) {
                    nanites -= i;
                    sync();
                    return true;
                }
            }
        } else {
            return true;
        }

        return false;
    }

    public void setNanites(int nanites)
    {
        this.nanites = nanites;
    }

    public int getNanites()
    {
        return nanites;
    }

    public int getMaxNanites()
    {
        return maxNanites;
    }

    public boolean increaseData(int i)
    {
        if (data < maxData) {
            if ((data + i) <= maxData) {
                if (decreaseNanites(i)) {
                    data += i;
                    sync();
                    return true;
                }
            }
        }

        return false;
    }

    public boolean decreaseData(int i)
    {
        if (!isPlayer() || (isPlayer() && !((EntityPlayer) entity).capabilities.isCreativeMode)) {
            if (data > 0) {
                if ((data - i) >= 0) {
                    data -= i;
                    sync();
                    return true;
                }
            }
        } else {
            return true;
        }

        return false;
    }

    public void setData(int data)
    {
        this.data = data;
    }

    public int getData()
    {
        return data;
    }

    public int getMaxData()
    {
        return maxData;
    }

    public void setOwner(EntityPlayer player)
    {
        owner = player.getCommandSenderName();
    }

    public String getOwner()
    {
        return owner.equals("none") ? null : owner;
    }

    public EntityPlayer getOwnerPlayer()
    {
        return owner.equals("none") ? null : entity.worldObj.getPlayerEntityByName(owner);
    }

}
