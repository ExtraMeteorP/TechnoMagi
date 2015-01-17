package com.ollieread.technomagi.tileentity.abstracts;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.ennds.research.IResearchMachine;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.network.message.MessageSyncTileEntityTM;
import com.ollieread.technomagi.util.PacketHelper;

public abstract class MachineResearch extends MachineInventory implements IResearchMachine
{

    protected int maxData = 100;
    public int data = 0;
    public int ticks;
    public int waiting;
    public boolean inProgress;
    protected Map<String, Integer> researching = new HashMap<String, Integer>();

    public MachineResearch(int capacity, int maxReceive, int maxExtract, int maxData, IInventory inventory)
    {
        super(capacity, maxReceive, maxExtract, inventory);

        this.maxData = maxData;
    }

    public boolean inProgress()
    {
        return inProgress;
    }

    public void setInProgress(boolean f)
    {
        inProgress = f;
        PacketHelper.syncTile(new MessageSyncTileEntityTM(this));
    }

    public int getData()
    {
        return data;
    }

    public void setData(int i)
    {
        data = i;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        ticks = compound.getInteger("Ticks");
        data = compound.getInteger("Data");
        inProgress = compound.getBoolean("InProgress");

        NBTTagList researchProgressList = compound.getTagList("ResearchProgress", compound.getId());
        researching = new HashMap<String, Integer>();

        for (int i = 0; i < researchProgressList.tagCount(); i++) {
            NBTTagCompound research = researchProgressList.getCompoundTagAt(i);
            researching.put(ResearchRegistry.getResearchName(research.getInteger("Research")), research.getInteger("Progress"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Ticks", ticks);
        compound.setInteger("Data", data);
        compound.setBoolean("InProgress", inProgress);

        NBTTagList researchProgressList = new NBTTagList();

        for (String k : researching.keySet()) {
            NBTTagCompound research = new NBTTagCompound();
            research.setInteger("Research", ResearchRegistry.getResearchId(k));
            research.setInteger("Progress", researching.get(k));
            researchProgressList.appendTag(research);
        }

        compound.setTag("ResearchProgress", researchProgressList);
    }

    public Map<String, Integer> getResearch()
    {
        return researching;
    }

    public void addResearch(String name, int progress)
    {
        if (researching.containsKey(name)) {
            researching.put(name, researching.get(name) + progress);
            data += progress;
        } else {
            researching.put(name, progress);
            data += progress;
        }
    }

    public void removeResearch(String name)
    {
        if (researching.containsKey(name)) {
            int progress = researching.remove(name);
            setData(data - progress);
            sync();
        }
    }

    public boolean decreaseResearch(String name, int progress)
    {
        if (researching.containsKey(name)) {
            int value = researching.get(name);

            if (value <= progress) {
                researching.remove(name);
                setData(data - value);
                sync();
                return true;
            } else {
                researching.put(name, value - progress);
                setData(data - progress);
                sync();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasResearch(String name)
    {
        return researching.containsKey(name);
    }
}
