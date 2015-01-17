package com.ollieread.technomagi.tileentity;

import cofh.api.energy.IEnergyHandler;

import com.ollieread.technomagi.tileentity.component.IHasOwner;

public interface ITileEntityMachine extends IEnergyHandler, IHasOwner, ITileEntityFacing, IBlockHandler, ITileEntityGui
{

    public int getProgress();

    public int getProgress(int width);

    public void setProgress(int progress);

    public int getMaxProgress();

    public void setMaxProgress(int maxProgress);

    public int getUsage();

    public void setUsage(int usage);

    public boolean canProcess();

    public boolean isProcessing();

    public void process();

}
