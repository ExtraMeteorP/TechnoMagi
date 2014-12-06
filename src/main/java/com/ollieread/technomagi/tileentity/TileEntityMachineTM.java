package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

import com.ollieread.technomagi.common.proxy.BasicEnergy;
import com.ollieread.technomagi.common.proxy.PlayerLocked;

public abstract class TileEntityMachineTM extends TileEntityTM implements IEnergyHandler, IPlayerLocked
{

    protected BasicEnergy storage;
    protected PlayerLocked locked;
    public int progress;
    public int maxProgress;
    public int usage;

    public int getProgress(int width)
    {
        return progress / (maxProgress / width);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        progress = compound.getInteger("Progress");

        locked.readFromNBT(compound);
        storage.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Progress", progress);

        locked.writeToNBT(compound);
        storage.writeToNBT(compound);
    }

    /* ENERGY */

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return from.equals(ForgeDirection.DOWN) || from.equals(ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord)).getOpposite());
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        int r = storage.receiveEnergy(from, maxReceive, simulate);

        if (r > 0) {
            sync();
            return r;
        }

        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        int r = storage.extractEnergy(from, maxExtract, simulate);

        if (r > 0) {
            sync();
            return r;
        }

        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return storage.getEnergyStored(null);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return storage.getMaxEnergyStored(null);
    }

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
