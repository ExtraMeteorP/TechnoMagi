package com.ollieread.technomagi.api.tile;

import net.minecraft.entity.player.EntityPlayer;

public interface ITilePlayerOwned
{

    public EntityPlayer getPlayerEntity();

    public String getPlayer();

    public boolean isPlayer(EntityPlayer player);

    public boolean isPlayer(String player);

    public void setPlayer(EntityPlayer player);

    public void setPlayer(String player);

}
