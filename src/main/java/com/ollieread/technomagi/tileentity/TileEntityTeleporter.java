package com.ollieread.technomagi.tileentity;

import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.api.block.TileEntityPlayerLocked;

public class TileEntityTeleporter extends TileEntityPlayerLocked
{

    protected int cooldown = -1;

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Cooldown", cooldown);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        cooldown = compound.getInteger("Cooldown");
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

}
