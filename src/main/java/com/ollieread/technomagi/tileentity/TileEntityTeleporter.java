package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyStorage;

import com.ollieread.technomagi.common.proxy.BasicEnergy;
import com.ollieread.technomagi.common.proxy.PlayerLocked;

public class TileEntityTeleporter extends TileEntityTM implements IPlayerLocked, IEnergyStorage, IEnergyConnection
{

    protected int cooldown = -1;
    protected int partnerX = -1;
    protected int partnerY = -1;
    protected int partnerZ = -1;

    protected BasicEnergy storage = null;
    protected PlayerLocked locked = null;

    protected int mode = 0;

    protected int maxCooldown = 10;

    public TileEntityTeleporter()
    {
        locked = new PlayerLocked();
        storage = new BasicEnergy(3200);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Cooldown", cooldown);

        if (getBlockMetadata() == 1) {
            compound.setInteger("PartnerX", partnerX);
            compound.setInteger("PartnerY", partnerY);
            compound.setInteger("PartnerZ", partnerZ);
        }

        compound.setInteger("Mode", mode);

        storage.writeToNBT(compound);
        locked.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        cooldown = compound.getInteger("Cooldown");

        if (compound.hasKey("PartnerX")) {
            partnerX = compound.getInteger("PartnerX");
            partnerY = compound.getInteger("PartnerY");
            partnerZ = compound.getInteger("PartnerZ");
        }
        mode = compound.getInteger("Mode");

        storage.readFromNBT(compound);
        locked.readFromNBT(compound);
    }

    @Override
    public void updateEntity()
    {
        if (cooldown > -1) {
            if (cooldown == maxCooldown) {
                cooldown = -1;
            } else {
                cooldown++;
            }
        }
    }

    public void startCooldown()
    {
        if (cooldown == -1) {
            cooldown = 0;
        }
    }

    public boolean canUse(EntityLivingBase entity)
    {
        if (cooldown == -1) {
            if (mode == 0 && entity instanceof EntityPlayer) {
                return true;
            } else if (mode == 1 && entity instanceof EntityPlayer && isPlayer(((EntityPlayer) entity))) {
                return true;
            } else if (mode == 2 && !(entity instanceof EntityPlayer)) {
                return true;
            } else if (mode == 3) {
                return true;
            }
        }

        return false;
    }

    public boolean canPartner()
    {
        return partnerX == -1 && partnerY == -1 && partnerZ == -1;
    }

    public void partner(int x, int y, int z)
    {
        partnerX = x;
        partnerY = y;
        partnerZ = z;

        sync();
    }

    public TileEntityTeleporter getPartner()
    {
        if (!canPartner()) {
            TileEntity tile = worldObj.getTileEntity(partnerX, partnerY, partnerZ);

            if (tile != null && tile instanceof TileEntityTeleporter) {
                return (TileEntityTeleporter) tile;
            }
        }

        return null;
    }

    public void setMode(int mode)
    {
        this.mode = mode;

        if (getBlockMetadata() == 1) {
            TileEntityTeleporter partner = getPartner();

            if (partner != null) {
                partner.setModeChain(mode);
            }
        }

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        markDirty();
    }

    public void setModeChain(int mode)
    {
        this.mode = mode;

        sync();
    }

    public int getMode()
    {
        return mode;
    }

    /* Everything below is just a proxy for the interfaces */

    /* ENERGY */

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return storage.canConnectEnergy(from);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        return storage.receiveEnergy(ForgeDirection.DOWN, maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        return storage.extractEnergy(ForgeDirection.DOWN, maxExtract, simulate);
    }

    @Override
    public int getEnergyStored()
    {
        return storage.getEnergyStored(null);
    }

    @Override
    public int getMaxEnergyStored()
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
