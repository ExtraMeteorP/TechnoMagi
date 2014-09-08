package com.ollieread.technomagi.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTeleporter extends TileEntityPlayerLocked
{

    protected int cooldown = -1;
    protected int partnerX = -1;
    protected int partnerY = -1;
    protected int partnerZ = -1;

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
    }

    @Override
    public void updateEntity()
    {
        if (cooldown > -1) {
            if (cooldown == 40) {
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

    public boolean canUse()
    {
        return cooldown == -1;
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

        markDirty();
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

}
