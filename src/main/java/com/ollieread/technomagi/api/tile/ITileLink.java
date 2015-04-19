package com.ollieread.technomagi.api.tile;

import net.minecraft.tileentity.TileEntity;

public interface ITileLink
{

    public boolean isLinked();

    public boolean canLink(int x, int y, int z);

    public int getRange();

    public void setLink(int x, int y, int z);

    public ITileLink getLink();

    public TileEntity getTile();

    public void removeLink();

    public boolean isProxy();

}
