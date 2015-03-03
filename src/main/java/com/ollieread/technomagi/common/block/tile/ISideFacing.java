package com.ollieread.technomagi.common.block.tile;

import net.minecraftforge.common.util.ForgeDirection;

public interface ISideFacing
{

    public void setDirection(ForgeDirection direction);

    public ForgeDirection getDirection();

    public void rotate();

}
