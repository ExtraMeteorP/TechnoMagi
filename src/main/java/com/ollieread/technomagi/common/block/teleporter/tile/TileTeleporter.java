package com.ollieread.technomagi.common.block.teleporter.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.technomagi.common.block.tile.ITilePlayerRestricted;
import com.ollieread.technomagi.common.block.tile.TileBase;

public abstract class TileTeleporter extends TileBase implements ITilePlayerRestricted
{

    protected String player;
    protected boolean locked = false;
    protected List<String> allowedPlayers = new ArrayList<String>();

    protected int cooldown = 0;

    public boolean canUse(EntityPlayer player, boolean incoming)
    {
        if (cooldown == 0) {
            if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord) && worldObj.isAirBlock(xCoord, yCoord + 2, zCoord)) {
                if (isPlayerLocked()) {
                    return hasAccess(player);
                }

                return true;
            }
        }

        return false;
    }

    public abstract void use(EntityPlayer player, boolean incoming);

    public abstract void startCooldown(boolean incoming);

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (cooldown > 0) {
                cooldown--;
            }
        }
    }

    public void setCooldown(int cooldown)
    {
        this.cooldown = cooldown;
    }

    @Override
    public EntityPlayer getPlayerEntity()
    {
        return worldObj.getPlayerEntityByName(player);
    }

    @Override
    public String getPlayer()
    {
        return player;
    }

    @Override
    public boolean isPlayer(EntityPlayer player)
    {
        EntityPlayer owner = getPlayerEntity();

        return owner != null && owner.equals(player);
    }

    @Override
    public boolean isPlayer(String player)
    {
        return this.player != null && this.player.equals(player);
    }

    @Override
    public void setPlayer(EntityPlayer player)
    {
        setPlayer(player.getCommandSenderName());
    }

    @Override
    public void setPlayer(String player)
    {
        this.player = player;
    }

    @Override
    public boolean isPlayerLocked()
    {
        return locked;
    }

    @Override
    public boolean hasAccess(EntityPlayer player)
    {
        return hasAccess(player.getCommandSenderName());
    }

    @Override
    public boolean hasAccess(String player)
    {
        return isPlayer(player) || allowedPlayers.contains(player);
    }

    @Override
    public void addAccess(String player)
    {
        if (!hasAccess(player)) {
            allowedPlayers.add(player);
        }
    }

    @Override
    public void removeAccess(String player)
    {
        if (hasAccess(player)) {
            allowedPlayers.remove(player);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Cooldown", cooldown);

        if (player != null && !player.isEmpty()) {
            compound.setString("Player", player);
            compound.setBoolean("Locked", locked);
        }

        NBTTagList playerList = new NBTTagList();

        for (String player : allowedPlayers) {
            NBTTagCompound p = new NBTTagCompound();
            p.setString("Player", player);
            playerList.appendTag(p);
        }

        compound.setTag("AllowedPlayers", playerList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.cooldown = compound.getInteger("Cooldown");

        if (compound.hasKey("Player")) {
            player = compound.getString("Player");
            locked = compound.getBoolean("Locked");
        }

        NBTTagList playerList = compound.getTagList("AllowedPlayers", compound.getId());
        allowedPlayers = new ArrayList<String>();

        for (int i = 0; i < playerList.tagCount(); i++) {
            allowedPlayers.add(playerList.getCompoundTagAt(i).getString("Player"));
        }
    }

}
