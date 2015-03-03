package com.ollieread.technomagi.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;

public interface ITilePlayerRestricted extends ITilePlayerLocked
{

    public boolean hasAccess(EntityPlayer player);

    public boolean hasAccess(String player);

    public void addAccess(String player);

    public void removeAccess(String player);

}
