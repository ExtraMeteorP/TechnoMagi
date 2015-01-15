package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

import com.ollieread.technomagi.common.proxy.BasicEnergy;
import com.ollieread.technomagi.common.proxy.PlayerLocked;

public abstract class TileEntityMachine extends TileEntityTM implements IEnergyHandler, IPlayerLocked
{

    protected BasicEnergy energy;
    protected PlayerLocked locked;
    public int progress;
    public int maxProgress;
    public int usage;

    public TileEntityMachine(int capacity)
    {
        this(capacity, capacity, capacity);
    }

    public TileEntityMachine(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer);
    }

    public TileEntityMachine(int capacity, int maxReceive, int maxExtract)
    {
        energy = new BasicEnergy(capacity, maxReceive, maxExtract);
        locked = new PlayerLocked();
    }

    public int getProgress(int width)
    {
        return progress / (maxProgress / width);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        progress = compound.getInteger("Progress");
        locked.readFromNBT(compound.getCompoundTag("Locked"));
        energy.readFromNBT(compound.getCompoundTag("Energy"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Progress", progress);

        NBTTagCompound lockedCompound = new NBTTagCompound();
        locked.writeToNBT(lockedCompound);
        NBTTagCompound energyCompound = new NBTTagCompound();
        energy.writeToNBT(energyCompound);
        compound.setTag("Locked", lockedCompound);
        compound.setTag("Energy", energyCompound);
    }

    /* ENERGY */

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return from.equals(ForgeDirection.DOWN) || from.equals(ForgeDirection.getOrientation(getBlockMetadata()).getOpposite());
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        int r = energy.receiveEnergy(from, maxReceive, simulate);

        if (r > 0) {
            sync();
            return r;
        }

        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        int r = energy.extractEnergy(from, maxExtract, simulate);

        if (r > 0) {
            sync();
            return r;
        }

        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return energy.getEnergyStored(null);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return energy.getMaxEnergyStored(null);
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
