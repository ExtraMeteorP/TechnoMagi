package com.ollieread.technomagi.common.component;

import net.minecraft.nbt.NBTTagCompound;

public class Progress
{

    protected int maxProgress = 0;
    protected int progress = 0;

    public void setMaxProgress(int maxProgress)
    {
        this.maxProgress = maxProgress;
    }

    public void setProgress(int progress)
    {
        this.progress = progress;
    }

    public void incrementProgress()
    {
        this.progress++;
    }

    public void decrementProgress()
    {
        this.progress--;
    }

    public void resetProgress()
    {
        this.progress = 0;
    }

    public int getProgress()
    {
        return this.progress;
    }

    public int getMaxProgress()
    {
        return this.maxProgress;
    }

    public boolean isMaxProgress()
    {
        return this.progress >= this.maxProgress;
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("MaxProgress", this.maxProgress);
        compound.setInteger("Progress", this.progress);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        this.maxProgress = compound.getInteger("MaxProgress");
        this.progress = compound.getInteger("Progress");
    }

}
