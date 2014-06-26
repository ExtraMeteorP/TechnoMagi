package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.api.block.TileEntityPlayerLocked;

public class TileEntityObservationChamber extends TileEntityPlayerLocked
{

    private Integer entity = null;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Entity")) {
            entity = compound.getInteger("Entity");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (entity != null) {
            compound.setInteger("Entity", entity);
        }
    }

    public void setEntity(Integer integer)
    {
        this.entity = integer;
    }

    public EntityLiving getEntity()
    {
        if (entity != null) {
            return (EntityLiving) EntityList.createEntityByID(entity, worldObj);
        }

        return null;
    }

}
