package com.ollieread.technomagi.tileentity.component;

import net.minecraft.nbt.NBTTagCompound;

public class MasterBlock implements IHasMaster
{

    public int masterX = 0;
    public int masterY = 0;
    public int masterZ = 0;
    public boolean setup = false;

    public MasterBlock(boolean setup)
    {
        this.setup = setup;
    }

    @Override
    public void setMaster(int x, int y, int z)
    {
        masterX = x;
        masterY = y;
        masterZ = z;
        setup = true;
    }

    @Override
    public void setSetup(boolean setup)
    {
        this.setup = setup;
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("MasterX", masterX);
        compound.setInteger("MasterY", masterY);
        compound.setInteger("MasterZ", masterZ);
        compound.setBoolean("Setup", setup);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        masterX = compound.getInteger("MasterX");
        masterY = compound.getInteger("MasterY");
        masterZ = compound.getInteger("MasterZ");
        setup = compound.getBoolean("Setup");
    }

}
