package com.ollieread.technomagi.tileentity;

import net.minecraft.world.World;

public interface ITileEntityHasLink<E>
{

    public boolean isLinked();

    public void unlink();

    public void setLinked(int x, int y, int z);

    public E getLinked(World world);

}
