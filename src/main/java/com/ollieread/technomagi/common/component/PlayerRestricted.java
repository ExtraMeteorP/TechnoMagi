package com.ollieread.technomagi.common.component;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.tile.ITilePlayerRestricted;

public class PlayerRestricted implements ITilePlayerRestricted
{

    protected boolean locked = false;
    protected String owner = null;
    protected List<String> players = new ArrayList<String>();

    public void setLocked(boolean locked)
    {
        this.locked = locked;
    }

    @Override
    public boolean isPlayerLocked()
    {
        return this.locked;
    }

    @Override
    public EntityPlayer getPlayerEntity()
    {
        return null;
    }

    @Override
    public String getPlayer()
    {
        return this.owner;
    }

    @Override
    public boolean isPlayer(EntityPlayer player)
    {
        return isPlayer(player.getCommandSenderName());
    }

    @Override
    public boolean isPlayer(String player)
    {
        return this.owner != null && this.owner.equals(player);
    }

    @Override
    public void setPlayer(EntityPlayer player)
    {
        setPlayer(player.getCommandSenderName());
    }

    @Override
    public void setPlayer(String player)
    {
        this.owner = player;
    }

    @Override
    public boolean hasAccess(EntityPlayer player)
    {
        return hasAccess(player.getCommandSenderName());
    }

    @Override
    public boolean hasAccess(String player)
    {
        return (this.owner != null && this.owner.equals(player)) || this.players.contains(player);
    }

    @Override
    public void addAccess(String player)
    {
        if (!this.players.contains(player)) {
            this.players.add(player);
        }
    }

    @Override
    public void removeAccess(String player)
    {
        if (this.players.contains(player)) {
            this.players.remove(player);
        }
    }

}
