package com.ollieread.technomagi.tileentity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.ennds.research.IResearchMachine;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.network.message.MessageSyncTileEntityTM;
import com.ollieread.technomagi.util.PacketHelper;

public class TileEntityResearch extends TileEntityTM implements IResearchMachine
{

    public int data = 0;
    public int progress = 0;
    public int ticks;
    public int waiting;
    public boolean inProgress;
    protected boolean isConnected = false;
    protected Map<String, Integer> researchingKnowledge = new HashMap<String, Integer>();

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
    public boolean isConnected()
    {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        ticks = compound.getInteger("Ticks");
        data = compound.getInteger("Data");
        inProgress = compound.getBoolean("InProgress");
        progress = compound.getInteger("Progress");
        isConnected = compound.getBoolean("Connected");

        NBTTagList researchProgressList = compound.getTagList("ResearchProgress", compound.getId());
        researchingKnowledge = new HashMap<String, Integer>();

        for (int i = 0; i < researchProgressList.tagCount(); i++) {
            NBTTagCompound research = researchProgressList.getCompoundTagAt(i);
            researchingKnowledge.put(ResearchRegistry.getResearchName(research.getInteger("Research")), research.getInteger("Progress"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Ticks", ticks);
        compound.setInteger("Data", data);
        compound.setBoolean("InProgress", inProgress);
        compound.setInteger("Progress", progress);
        compound.setBoolean("Connected", isConnected);

        NBTTagList researchProgressList = new NBTTagList();

        for (String k : researchingKnowledge.keySet()) {
            NBTTagCompound research = new NBTTagCompound();
            research.setInteger("Research", ResearchRegistry.getResearchId(k));
            research.setInteger("Progress", researchingKnowledge.get(k));
            researchProgressList.appendTag(research);
        }

        compound.setTag("ResearchProgress", researchProgressList);
    }

    public Map<String, Integer> getKnowledge()
    {
        return researchingKnowledge;
    }

    public void addResearch(String name, int progress)
    {
        if (researchingKnowledge.containsKey(name)) {
            researchingKnowledge.put(name, researchingKnowledge.get(name) + progress);
            data += progress;
        } else {
            researchingKnowledge.put(name, progress);
            data += progress;
        }
    }

    public void removeKnowledge(String name)
    {
        if (researchingKnowledge.containsKey(name)) {
            int progress = researchingKnowledge.remove(name);
            setData(data - progress);
            sync();
        }
    }

    public boolean decreaseKnowledge(String name, int progress)
    {
        if (researchingKnowledge.containsKey(name)) {
            int value = researchingKnowledge.get(name);

            if (value <= progress) {
                researchingKnowledge.remove(name);
                setData(data - value);
                sync();
                return true;
            } else {
                researchingKnowledge.put(name, value - progress);
                setData(data - progress);
                sync();
                return true;
            }
        }

        return false;
    }

}
