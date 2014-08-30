package com.ollieread.technomagi.tileentity;

import java.util.Random;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.init.Blocks;

public class TileEntityObservationChamber extends TileEntityResearch implements IHasFiller
{

    protected int entity = -1;
    protected int profession = -1;
    protected int health = 0;
    protected Random rand = new Random();

    public TileEntityObservationChamber()
    {
        super(2);
    }

    public void setEntity(EntityLivingBase entity)
    {
        this.entity = EntityList.getEntityID(entity);

        if (entity instanceof EntityVillager) {
            this.profession = ((EntityVillager) entity).getProfession();
        }

        health = (int) entity.getHealth();
    }

    public void setEntity(Integer integer)
    {
        this.entity = integer;

        EntityLivingBase entityLiving = (EntityLivingBase) getEntityLiving();

        if (entityLiving != null) {
            health = (int) entityLiving.getHealth();
        }
    }

    public int getEntity()
    {
        return entity;
    }

    public EntityLiving getEntityLiving()
    {
        if (entity > -1) {
            EntityLiving entityLiving = (EntityLiving) EntityList.createEntityByID(entity, worldObj);

            if (entityLiving instanceof EntityVillager) {
                ((EntityVillager) entityLiving).setProfession(profession);
            }

            return entityLiving;
        }

        return null;
    }

    public int getHealth()
    {
        return health;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        health = compound.getInteger("Health");

        if (compound.hasKey("Entity")) {
            entity = compound.getInteger("Entity");
            profession = compound.getInteger("Profession");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Health", health);

        if (entity > -1) {
            compound.setInteger("Entity", entity);
            compound.setInteger("Profession", profession);
        }
    }

    @Override
    public void create()
    {
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
