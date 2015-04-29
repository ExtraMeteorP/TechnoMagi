package com.ollieread.technomagi.common.block.machine.tile;

import net.minecraft.nbt.NBTTagCompound;

public class TileCultivatorBasic extends TileCultivator
{

    public TileCultivatorBasic()
    {
        super();

        maxNanites = 75;
        maxSample = 75;
        maxCultivated = 150;
        consumeNanites = 5;
        consumeSample = 5;
        modifier = 0.5F;
        chance = 6;
    }

    @Override
    public void consume()
    {

    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
    }

}
