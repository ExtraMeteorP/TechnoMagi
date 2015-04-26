package com.ollieread.technomagi.common.block.teleporter.tile;

import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.block.tile.TileBase;

public class TileElevator extends TileBase
{

    protected int cooldown = 0;

    public void startCooldown(boolean incoming)
    {
        this.cooldown = 30;
    }

    public boolean isOnCooldown()
    {
        return this.cooldown > 0;
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (this.cooldown > 0) {
                this.cooldown--;
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Cooldown", this.cooldown);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.cooldown = compound.getInteger("Cooldown");
    }

}
