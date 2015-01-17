package com.ollieread.technomagi.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileEntityLocked
{

    public boolean hasAccess(String name);

    public void addAccess(String name);

    public void removeAccess(String name);

    public void readFromNBT(NBTTagCompound compound);

    public void writeToNBT(NBTTagCompound compound);

}
