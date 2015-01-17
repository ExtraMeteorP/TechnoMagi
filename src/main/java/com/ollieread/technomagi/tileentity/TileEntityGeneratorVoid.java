package com.ollieread.technomagi.tileentity;

import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityGenerator;

public class TileEntityGeneratorVoid extends TileEntityGenerator
{

    public TileEntityGeneratorVoid()
    {
        super(Config.generatorVoidPowerMax, Config.generatorVoidPowerOutput, 0, Config.generatorVoidMaxTicks);
    }

    public void setGenerationByLocation(int y)
    {
        int generation = (int) Math.ceil((256 - y) / (256 / Config.generatorVoidModifier));

        energyGeneration = generation;
    }

    @Override
    public boolean canGenerate()
    {
        return true;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return from.equals(ForgeDirection.UP);
    }

}
