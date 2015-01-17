package com.ollieread.technomagi.tileentity.abstracts;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.technomagi.event.PocketEvent;
import com.ollieread.technomagi.tileentity.ITileEntityPocket;

public abstract class Pocket extends Basic implements ITileEntityPocket
{

    protected boolean negative = false;
    protected int ticks = 0;
    protected int size = 16;

    public Pocket(int size)
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
        }
    }

}
