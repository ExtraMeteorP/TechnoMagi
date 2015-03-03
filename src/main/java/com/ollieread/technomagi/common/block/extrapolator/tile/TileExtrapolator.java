package com.ollieread.technomagi.common.block.extrapolator.tile;

import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.block.tile.TileBase;

public class TileExtrapolator extends TileBase
{

    public int innerRotation;
    protected boolean activated = true;

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Rotation", innerRotation);
        compound.setBoolean("Activated", activated);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        innerRotation = compound.getInteger("Rotation");
        activated = compound.getBoolean("Activated");
    }

    public void activate()
    {
        this.activated = true;
    }

    public boolean isActivated()
    {
        return this.activated;
    }

    @Override
    public void updateEntity()
    {
        this.innerRotation++;
    }

}
