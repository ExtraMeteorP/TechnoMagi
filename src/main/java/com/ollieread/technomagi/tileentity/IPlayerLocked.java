package com.ollieread.technomagi.tileentity;

public interface IPlayerLocked
{

    public boolean hasPlayer();

    public void setPlayer(String name);

    public String getPlayer();

    public boolean isPlayer(String name);

}
