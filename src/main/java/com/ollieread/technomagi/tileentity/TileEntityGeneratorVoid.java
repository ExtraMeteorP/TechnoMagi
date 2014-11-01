package com.ollieread.technomagi.tileentity;

public class TileEntityGeneratorVoid extends TileEntityGenerator
{

    public TileEntityGeneratorVoid()
    {
        super(3200, 50, 0, 5);
    }

    public void setGenerationByLocation(int y)
    {
        int generation = (int) Math.ceil((256 - y) / (256 / 5));

        energyGeneration = generation;
    }

    @Override
    public boolean canGenerate()
    {
        return true;
    }

}
