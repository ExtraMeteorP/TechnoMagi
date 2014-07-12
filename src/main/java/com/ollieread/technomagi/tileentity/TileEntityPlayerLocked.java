package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityPlayerLocked extends TileEntityTM implements IPlayerLocked
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

    public boolean isPlayer(EntityPlayer player)
    {
        return isPlayer(player.getCommandSenderName());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        player = compound.getString("Player");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setString("Player", player);
    }

}