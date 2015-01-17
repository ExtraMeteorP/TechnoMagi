package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface ITileEntityHasOwner
{

    public void setOwner(String name);

    public boolean isOwner(String name);

    public EntityPlayer getOwner(World world);

}
