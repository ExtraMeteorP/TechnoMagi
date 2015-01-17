package com.ollieread.technomagi.tileentity.component;

import net.minecraft.nbt.NBTTagCompound;

public interface ILocked
{

    public boolean hasAccess(String name);

    public void addAccess(String name);

    public void removeAccess(String name);

    public void readFromNBT(NBTTagCompound compound);

    public void writeToNBT(NBTTagCompound compound);

}
