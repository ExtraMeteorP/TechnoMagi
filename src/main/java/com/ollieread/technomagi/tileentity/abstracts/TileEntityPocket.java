package com.ollieread.technomagi.tileentity.abstracts;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.technomagi.event.PocketEvent;
import com.ollieread.technomagi.tileentity.ITileEntityPocket;
import com.ollieread.technomagi.world.pocket.PocketManager;

public abstract class TileEntityPocket extends TileEntityBasic implements ITileEntityPocket
{

    protected boolean negative = false;
    protected int ticks = 0;
    protected int size = 16;
    protected boolean synced = false;

    public TileEntityPocket(int size)
    {
        this.size = size;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        negative = compound.getBoolean("Negative");
        ticks = compound.getInteger("Ticks");
        size = compound.getInteger("Size");
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setBoolean("Negative", negative);
        compound.setInteger("Ticks", ticks);
        compound.setInteger("Size", size);
    }

    public void setNegative(boolean negative)
    {
        this.negative = negative;
    }

    public int getSize()
    {
        return size;
    }

    @Override
    public boolean isNegative()
    {
        return negative;
    }

    @Override
    public boolean isInside(int x, int z)
    {
        int d = size / 2;
        return x >= (xCoord - d) && x <= (xCoord + d) && z >= (zCoord - d) && z <= (zCoord + d);
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            ticks++;

            if (shouldPerform(ticks)) {
                if (!MinecraftForge.EVENT_BUS.post(new PocketEvent(this))) {
                    return;
                }

                perform();
                ticks = 0;
            }

            if (!synced) {
                PocketManager.getInstance(worldObj.provider.dimensionId).addPocket(this);
                synced = true;
            }
        }
    }

}
