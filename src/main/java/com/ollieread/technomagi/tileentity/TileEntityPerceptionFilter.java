package com.ollieread.technomagi.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.technomagi.world.region.IRegionController;
import com.ollieread.technomagi.world.region.RegionManager;
import com.ollieread.technomagi.world.region.RegionManager.RegionControllerType;

public class TileEntityPerceptionFilter extends TileEntityTM implements IRegionController
{

    protected int network = -1;
    protected List<String> players = new ArrayList<String>();
    protected boolean enabled = true;
    protected boolean synced = false;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        network = compound.getInteger("Network");
        enabled = compound.getBoolean("Enabled");

        NBTTagList playerList = compound.getTagList("Players", compound.getId());
        players = new ArrayList<String>();

        for (int i = 0; i < playerList.tagCount(); i++) {
            NBTTagCompound research = playerList.getCompoundTagAt(i);
            players.add(research.getString("Player"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

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

    @Override
    public int[] getCoords()
    {
        return new int[] { xCoord, yCoord, zCoord };
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (network == -1) {
                int id = RegionManager.getNetworkForCoords(xCoord, zCoord);

                if (!RegionManager.hasController(id, getType())) {
                    network = id;
                    RegionManager.addController(this);
                    synced = true;
                }
            }
        }
    }

}
