package com.ollieread.technomagi.tileentity;

public class TileEntityGeneratorLight extends TileEntityGenerator
{

    public TileEntityGeneratorLight()
    {
        super(3200, 10, 2, 5);
    }

    @Override
    public boolean canGenerate()
    {
        return worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord) && worldObj.isDaytime();
    }

}
