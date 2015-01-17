package com.ollieread.technomagi.tileentity;

public interface ITileEntityPocket
{

    public boolean isNegative();

    public int getSize();

    public boolean shouldPerform(int ticks);

    public void perform();

    public PocketType getType();

    public int getModifier(double distance);

    public static enum PocketType {
        AREA, EVENT, PLAYER
    }

}
