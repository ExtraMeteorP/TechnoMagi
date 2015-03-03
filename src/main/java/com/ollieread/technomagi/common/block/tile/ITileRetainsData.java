package com.ollieread.technomagi.common.block.tile;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileRetainsData
{

    public NBTTagCompound getRetainedData();

    public void setRetainedData(NBTTagCompound compound);

}
