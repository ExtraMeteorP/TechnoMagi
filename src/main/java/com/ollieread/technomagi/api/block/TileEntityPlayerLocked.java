package com.ollieread.technomagi.api.block;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPlayerLocked extends TileEntity implements IPlayerLocked
{

    protected String player = "none";

    @Override
    public void setPlayer(String name)
    {
        if (player.equals("none")) {
            player = name;
        }
    }

    @Override
    public String getPlayer(String name)
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

    public void readFromNBT(NBTTagCompound compound)
    {
        compound.setString("Player", player);
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        player = compound.getString("Player");
    }

}