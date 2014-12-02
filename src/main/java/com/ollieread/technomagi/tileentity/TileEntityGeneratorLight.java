package com.ollieread.technomagi.tileentity;

import com.ollieread.technomagi.common.init.Config;

public class TileEntityGeneratorLight extends TileEntityGenerator
{

    public TileEntityGeneratorLight()
    {
        super(Config.generatorLightPowerMax, Config.generatorLightPowerOutput, Config.generatorLightGeneration, Config.generatorLightMaxTicks);
    }

    @Override
    public boolean canGenerate()
    {
        return worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord) && worldObj.isDaytime();
    }

}
