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

    protected String entity = null;
    protected int profession = -1;
    protected int health = 0;
    protected Random rand = new Random();

    public TileEntityObservationChamber()
    {
        super(2);
    }

    public void setEntity(EntityLivingBase entity)
    {
        this.entity = (String) EntityList.classToStringMapping.get(entity.getClass());

        if (entity instanceof EntityVillager) {
            this.profession = ((EntityVillager) entity).getProfession();
        }

        health = (int) entity.getHealth();
    }

    public String getEntity()
    {
        return entity;
    }

    public EntityLiving getEntityLiving()
    {
        if (entity != null) {
            EntityLiving entityLiving = (EntityLiving) EntityList.createEntityByName(entity, worldObj);

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
            entity = compound.getString("Entity");
            profession = compound.getInteger("Profession");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Health", health);

        if (entity != null) {
            compound.setString("Entity", entity);
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
