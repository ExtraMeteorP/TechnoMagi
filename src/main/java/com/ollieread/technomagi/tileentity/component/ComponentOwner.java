package com.ollieread.technomagi.tileentity.component;

import com.ollieread.technomagi.tileentity.ITileEntityHasOwner;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ComponentOwner implements ITileEntityHasOwner
{

    protected String owner;

    @Override
    public void setOwner(String name)
    {
        owner = name;
    }

    @Override
    public boolean isOwner(String name)
    {
        return owner != null && owner.equals(name);
    }

    @Override
    public EntityPlayer getOwner(World world)
    {
        return owner != null && !owner.isEmpty() ? world.getPlayerEntityByName(owner) : null;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        String nbtOwner = compound.getString("Owner");

        if (nbtOwner.equals("none")) {
            owner = null;
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        if (owner == null || owner.isEmpty()) {
            compound.setString("Owner", "none");
        } else {
            compound.setString("Owner", owner);
        }
    }

}
