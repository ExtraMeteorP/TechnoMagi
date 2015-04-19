package com.ollieread.technomagi.api.tile;

public interface ITileProcessor
{

    public boolean canProcess();

    public void process();

    public boolean isProcessing();

    public int getProgress();

    public int getProgressScaled(int scale);

}
