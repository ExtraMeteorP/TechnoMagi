package com.ollieread.technomagi.api.tile;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileRetainsData
{

    public NBTTagCompound getRetainedData();

    public void setRetainedData(NBTTagCompound compound);

}
