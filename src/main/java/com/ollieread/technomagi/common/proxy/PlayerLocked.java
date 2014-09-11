package com.ollieread.technomagi.common.proxy;

import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.tileentity.IPlayerLocked;

public class PlayerLocked implements IPlayerLocked
{

    protected String player = "none";

    @Override
    public boolean hasPlayer()
    {
        return player.equals("none");
    }

    @Override
    public void setPlayer(String name)
    {
        if (player.equals("none")) {
            player = name;
        }
    }

    @Override
    public String getPlayer()
    {
        return !player.equals("none") ? player : null;
    }

    @Override
    public boolean isPlayer(String name)
    {
        return !player.equals("none") && player.equals(name);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        player = compound.getString("Player");
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Player", player);
    }

}
