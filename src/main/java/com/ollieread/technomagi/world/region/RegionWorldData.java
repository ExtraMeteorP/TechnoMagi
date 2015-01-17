package com.ollieread.technomagi.world.region;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class RegionWorldData extends WorldSavedData
{

    private static final String IDENT = "RegionNetworks";
    private int dimension;

    public RegionWorldData(int dimension)
    {
        super(IDENT);

        this.dimension = dimension;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        RegionManager.getInstance(dimension).readFromNBT(compound);
        compound.setInteger("Dimension", dimension);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        RegionManager.getInstance(dimension).writeToNBT(compound);
        dimension = compound.getInteger("Dimension");
    }

    public static RegionWorldData get(World world)
    {
        RegionWorldData data = (RegionWorldData) world.loadItemData(RegionWorldData.class, IDENT);

        if (data == null) {
            data = new RegionWorldData(world.provider.dimensionId);
            world.setItemData(IDENT, data);
        }

        return data;
    }

}
