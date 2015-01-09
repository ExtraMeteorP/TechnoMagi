package com.ollieread.technomagi.common.proxy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LinkedTile<E>
{

    public int linkedX = 0;
    public int linkedY = 0;
    public int linkedZ = 0;
    public boolean setup = false;

    public boolean isLinked()
    {
        return setup;
    }

    public void unlink()
    {
        linkedX = -1;
        linkedY = -1;
        linkedZ = -1;
        setup = true;
    }

    public void setLinked(int x, int y, int z)
    {
        linkedX = x;
        linkedY = y;
        linkedZ = z;
        setup = true;
    }

    public E getLinked(World world)
    {
        return (E) world.getTileEntity(linkedX, linkedY, linkedZ);
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("LinkedX", linkedX);
        compound.setInteger("LinkedY", linkedY);
        compound.setInteger("LinkedZ", linkedZ);
        compound.setBoolean("Setup", setup);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        linkedX = compound.getInteger("LinkedX");
        linkedY = compound.getInteger("LinkedY");
        linkedZ = compound.getInteger("LinkedZ");
        setup = compound.getBoolean("Setup");
    }

}
