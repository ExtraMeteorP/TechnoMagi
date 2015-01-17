package com.ollieread.technomagi.tileentity.component;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IHasOwner
{

    public void setOwner(String name);

    public boolean isOwner(String name);

    public EntityPlayer getOwner(World world);

}
