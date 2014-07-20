package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.init.Blocks;

public class TileEntityObservationChamber extends TileEntityPlayerLocked implements IHasFiller
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

    @Override
    public void create()
    {
        System.out.println("Called");
        worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.blockEmptyFiller);
        worldObj.setBlock(xCoord, yCoord + 2, zCoord, Blocks.blockEmptyFiller);

        if (!worldObj.isRemote) {
            ((TileEntityEmptyFiller) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord)).setParent(xCoord, yCoord, zCoord);
            ((TileEntityEmptyFiller) worldObj.getTileEntity(xCoord, yCoord + 2, zCoord)).setParent(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void destroy()
    {
        worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
        worldObj.setBlockToAir(xCoord, yCoord + 2, zCoord);
        invalidate();
        worldObj.setBlockToAir(xCoord, yCoord, zCoord);
    }

}
