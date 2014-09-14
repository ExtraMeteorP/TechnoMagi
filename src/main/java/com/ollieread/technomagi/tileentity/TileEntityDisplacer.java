package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.proxy.PlayerLocked;

public class TileEntityDisplacer extends TileEntityTM implements IPlayerLocked
{

    protected PlayerLocked locked = null;

    protected boolean on;

    public TileEntityDisplacer()
    {
        locked = new PlayerLocked();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        on = compound.getBoolean("On");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("On", on);
    }

    public boolean isOn()
    {
        return on;
    }

    public void toggleStatus()
    {
        if (!worldObj.isRemote) {
            if (on) {
                on = false;
            } else {
                on = true;

                if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord)) {
                    worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.blockDisplacedAir);
                    TileEntityDisplacedAir air = (TileEntityDisplacedAir) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);

                    if (air != null) {
                        air.setMaster(xCoord, yCoord, zCoord);
                    }
                }
            }
        }
    }

    /* Everything below is just a proxy for the interfaces */

    /* LOCKED */

    @Override
    public boolean hasPlayer()
    {
        return locked.hasPlayer();
    }

    @Override
    public void setPlayer(String name)
    {
        locked.setPlayer(name);
    }

    @Override
    public String getPlayer()
    {
        return locked.getPlayer();
    }

    @Override
    public boolean isPlayer(String name)
    {
        return locked.isPlayer(name);
    }

    public boolean isPlayer(EntityPlayer player)
    {
        return isPlayer(player.getCommandSenderName());
    }

}
