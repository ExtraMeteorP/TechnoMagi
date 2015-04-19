package com.ollieread.technomagi.api.tile;

import net.minecraftforge.common.util.ForgeDirection;

public interface ISideFacing
{

    public void setDirection(ForgeDirection direction);

    public ForgeDirection getDirection();

    public void rotate();

}
