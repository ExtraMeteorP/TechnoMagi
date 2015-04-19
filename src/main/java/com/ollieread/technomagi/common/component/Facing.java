package com.ollieread.technomagi.common.component;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.api.tile.ISideFacing;

public class Facing implements ISideFacing
{

    protected ForgeDirection direction;

    @Override
    public void setDirection(ForgeDirection direction)
    {
        this.direction = direction;
    }

    @Override
    public ForgeDirection getDirection()
    {
        return direction;
    }

    @Override
    public void rotate()
    {

    }

    public void writeToNBT(NBTTagCompound compound)
    {
        if (direction != null) {
            compound.setInteger("Direction", direction.ordinal());
        }
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        if (compound.hasKey("Direction")) {
            direction = ForgeDirection.values()[compound.getInteger("Direction")];
        }
    }

}
