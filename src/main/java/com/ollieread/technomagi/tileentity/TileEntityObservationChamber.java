package com.ollieread.technomagi.tileentity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.init.Blocks;

public class TileEntityObservationChamber extends TileEntityInventoryLocked implements IHasFiller
{

    protected int entity = -1;
    protected int health = 0;
    protected int data = 0;
    protected int progress = 0;
    protected int ticks;
    protected int waiting;
    protected boolean inProgress;
    protected Random rand = new Random();
    protected Map<String, Integer> researchingKnowledge = new HashMap<String, Integer>();

    public TileEntityObservationChamber()
    {
        super(2);
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
            return (EntityLiving) EntityList.createEntityByID(entity, worldObj);
        }

        return null;
    }

    public int getHealth()
    {
        return health;
    }

    public boolean inProgress()
    {
        return inProgress;
    }

    public void setInProgress(boolean f)
    {
        inProgress = f;
        markDirty();
    }

    public int getData()
    {
        return data;
    }

    public int getProgress()
    {
        return progress;
    }

    public void setData(int i)
    {
        data = i;
    }

    public void setProgress(int i)
    {
        progress = i;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        health = compound.getInteger("Health");
        ticks = compound.getInteger("Ticks");
        data = compound.getInteger("Data");
        inProgress = compound.getBoolean("InProgress");
        progress = compound.getInteger("Progress");

        NBTTagList researchProgressList = compound.getTagList("ResearchProgress", compound.getId());
        researchingKnowledge = new HashMap<String, Integer>();

        for (int i = 0; i < researchProgressList.tagCount(); i++) {
            NBTTagCompound research = researchProgressList.getCompoundTagAt(i);
            researchingKnowledge.put(ResearchRegistry.getResearchName(research.getInteger("Research")), research.getInteger("Progress"));
        }

        if (compound.hasKey("Entity")) {
            entity = compound.getInteger("Entity");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Health", health);
        compound.setInteger("Ticks", ticks);
        compound.setInteger("Data", data);
        compound.setBoolean("InProgress", inProgress);
        compound.setInteger("Progress", progress);

        NBTTagList researchProgressList = new NBTTagList();

        for (String k : researchingKnowledge.keySet()) {
            NBTTagCompound research = new NBTTagCompound();
            research.setInteger("Research", ResearchRegistry.getResearchId(k));
            research.setInteger("Progress", researchingKnowledge.get(k));
            researchProgressList.appendTag(research);
        }

        compound.setTag("ResearchProgress", researchProgressList);

        if (entity > -1) {
            compound.setInteger("Entity", entity);
        }
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
