package com.ollieread.technomagi.tileentity.component;

import net.minecraft.world.World;

public interface IHasLink<E>
{

    public boolean isLinked();

    public void unlink();

    public void setLinked(int x, int y, int z);

    public E getLinked(World world);

}
