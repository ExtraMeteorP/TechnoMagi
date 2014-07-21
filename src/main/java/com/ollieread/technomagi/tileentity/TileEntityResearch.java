package com.ollieread.technomagi.tileentity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.ennds.research.IResearchMachine;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.network.message.MessageSyncTileEntityTM;
import com.ollieread.technomagi.util.PacketHelper;

public class TileEntityResearch extends TileEntityInventoryLocked implements IResearchMachine
{

    protected int data = 0;
    protected int progress = 0;
    protected int ticks;
    protected int waiting;
    protected boolean inProgress;
    protected boolean isConnected;
    protected Map<String, Integer> researchingKnowledge = new HashMap<String, Integer>();

    public TileEntityResearch(int size)
    {
        super(size);
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
        return isConnected;
    }

    protected void reduceStacks(int i)
    {
        for (int x = 0; x < getSizeInventory(); x++) {
            decrStackSize(x, i);
        }
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

    public void addResearch(String name, int progress)
    {
        if (researchingKnowledge.containsKey(name)) {
            researchingKnowledge.put(name, researchingKnowledge.get(name) + progress);
        } else {
            researchingKnowledge.put(name, progress);
        }
    }

}
