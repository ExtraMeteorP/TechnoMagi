package com.ollieread.technomagi.common.proxy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.technomagi.world.region.IRegionController;
import com.ollieread.technomagi.world.region.RegionManager.RegionControllerType;

public abstract class RegionDamage implements IRegionController
{

    protected int network = -1;
    protected List<String> players = new ArrayList<String>();
    protected boolean enabled = true;

    public void readFromNBT(NBTTagCompound compound)
    {
        network = compound.getInteger("Network");
        enabled = compound.getBoolean("Enabled");

        NBTTagList playerList = compound.getTagList("Players", compound.getId());
        players = new ArrayList<String>();

        for (int i = 0; i < playerList.tagCount(); i++) {
            NBTTagCompound research = playerList.getCompoundTagAt(i);
            players.add(research.getString("Player"));
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Network", network);
        compound.setBoolean("Enabled", enabled);

        NBTTagList playerList = new NBTTagList();

        for (String player : players) {
            NBTTagCompound playerCompound = new NBTTagCompound();
            playerCompound.setString("Player", player);
            playerList.appendTag(playerCompound);
        }

        compound.setTag("Players", playerList);
    }

    public void setNetwork(int id)
    {
        network = id;
    }

    @Override
    public int getNetworkId()
    {
        return network;
    }

    @Override
    public RegionControllerType getType()
    {
        return RegionControllerType.PERCEPTION;
    }

    @Override
    public boolean affectsEntity(EntityLivingBase entity)
    {
        if (entity instanceof EntityPlayer) {
            return players.contains(((EntityPlayer) entity).getCommandSenderName());
        }

        return false;
    }

    @Override
    public boolean enabled()
    {
        return enabled;
    }

}
