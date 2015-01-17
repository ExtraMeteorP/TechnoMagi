package com.ollieread.technomagi.tileentity;

import cofh.api.energy.IEnergyHandler;

public interface ITileEntityMachine extends IEnergyHandler, ITileEntityHasOwner, ITileEntityFacing, ITileEntityBlockHandler, ITileEntityGui
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
