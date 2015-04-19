package com.ollieread.technomagi.common.block.structure.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.api.tile.ISideFacing;
import com.ollieread.technomagi.common.block.tile.TileBase;

public abstract class TileStructure extends TileBase implements ISideFacing
{

    protected boolean enabled = false;
    protected ForgeDirection direction;

    public boolean isEnabled()
    {
        return enabled;
    }

    public abstract void enable();

    public abstract void disable();

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("Enabled", enabled);

        if (direction != null) {
            compound.setInteger("Direction", direction.ordinal());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        enabled = compound.getBoolean("Enabled");

        if (compound.hasKey("Direction")) {
            direction = ForgeDirection.values()[compound.getInteger("Direction")];
        }
    }

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
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;

        for (ForgeDirection direction : directions) {
            if (direction.equals(getDirection()) || direction.equals(ForgeDirection.UP) || direction.equals(ForgeDirection.DOWN)) {
                continue;
            }

            setDirection(direction);
            markDirty();
            break;
        }
    }

}
