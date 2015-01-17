package com.ollieread.technomagi.tileentity.component;

import java.util.ArrayList;
import java.util.List;

import com.ollieread.technomagi.tileentity.ITileEntityLocked;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ComponentLocked implements ITileEntityLocked
{

    private List<String> allowedPlayers = new ArrayList<String>();

    @Override
    public boolean hasAccess(String name)
    {
        return allowedPlayers.contains(name);
    }

    @Override
    public void addAccess(String name)
    {
        if (!allowedPlayers.contains(name)) {
            allowedPlayers.add(name);
        }
    }

    @Override
    public void removeAccess(String name)
    {
        allowedPlayers.remove(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagList playerList = compound.getTagList("AllowedPlayers", compound.getId());
        allowedPlayers = new ArrayList<String>();

        for (int i = 0; i < playerList.tagCount(); i++) {
            NBTTagCompound playerCompound = playerList.getCompoundTagAt(i);
            allowedPlayers.add(playerCompound.getString("Player"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList playerList = new NBTTagList();

        for (String player : allowedPlayers) {
            NBTTagCompound playerCompound = new NBTTagCompound();
            playerCompound.setString("Player", player);
            playerList.appendTag(playerCompound);
        }

        compound.setTag("AllowedPlayers", playerList);
    }

}
