package com.ollieread.technomagi.tileentity;

public class TileEntityGeneratorBasic extends TileEntityPower
{

    protected int energyGeneration = 0;

    public TileEntityGeneratorBasic()
    {
        super(5120, 0, 10);
    }

    public void setGenerationByLocation(int y)
    {
        int generation = (int) Math.ceil((256 - y) / (256 / 5));

        energyGeneration = generation;
    }

    public void updateEntity()
    {
        if (getEnergyStored() < getMaxEnergyStored()) {
            energy.modifyEnergyStored(energyGeneration);
        }
    }

}
