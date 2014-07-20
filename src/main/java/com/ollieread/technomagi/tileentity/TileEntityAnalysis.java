package com.ollieread.technomagi.tileentity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.ResearchRegistry;

public class TileEntityAnalysis extends TileEntityInventoryLocked
{

    protected int data = 0;
    protected int progress = 0;
    protected int ticks;
    protected int waiting;
    protected boolean inProgress;
    protected Random rand = new Random();
    protected Map<String, Integer> researchingKnowledge = new HashMap<String, Integer>();

    public TileEntityAnalysis()
    {
        super(9, 64);
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
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (canAnalyse()) {
                if (inProgress()) {
                    analyse();
                }
            } else {
                waiting++;

                if (waiting == 30) {
                    waiting = 0;
                    setInProgress(false);
                }
            }
        }
    }

    public boolean canAnalyse()
    {
        if (data == 100) {
            return false;
        }
        IResearchAnalysis analysis = getResearchAnalysis();
        if (analysis != null) {
            IResearch research = (IResearch) analysis;
            EntityPlayer player = worldObj.getPlayerEntityByName(this.getPlayer());
            if (player != null && research.getProgress() + data > 100 && research.canPerform(ExtendedPlayerKnowledge.get(player))) {
                return false;
            }
            return true;
        }

        return false;
    }

    public void analyse()
    {
        ticks++;

        if (ticks >= 20) {
            ticks = 0;
            progress++;
        }

        if (progress >= 100) {
            IResearchAnalysis analysis = getResearchAnalysis();
            int c = rand.nextInt(analysis.getChance()) + 1;

            if (c == analysis.getChance()) {
                IResearch research = (IResearch) analysis;

                if (researchingKnowledge.containsKey(research.getKnowledge())) {
                    researchingKnowledge.put(research.getKnowledge(), researchingKnowledge.get(research.getKnowledge()) + research.getProgress());
                    data += research.getProgress();
                } else {
                    researchingKnowledge.put(research.getKnowledge(), research.getProgress());
                    data += research.getProgress();
                }
            }

            reduceStacks(1);
            setInProgress(false);
            progress = 0;
        }
    }

    private void reduceStacks(int i)
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

        NBTTagList researchProgressList = compound.getTagList("ResearchProgress", compound.getId());
        researchingKnowledge = new HashMap<String, Integer>();

        for (int i = 0; i < researchProgressList.tagCount(); i++) {
            NBTTagCompound research = researchProgressList.getCompoundTagAt(i);
            researchingKnowledge.put(ResearchRegistry.getResearchName(research.getInteger("Research")), research.getInteger("Progress"));
        }

        if (compound.hasKey("CustomName", 8)) {
            name = compound.getString("CustomName");
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

        NBTTagList researchProgressList = new NBTTagList();

        for (String k : researchingKnowledge.keySet()) {
            NBTTagCompound research = new NBTTagCompound();
            research.setInteger("Research", ResearchRegistry.getResearchId(k));
            research.setInteger("Progress", researchingKnowledge.get(k));
            researchProgressList.appendTag(research);
        }

        compound.setTag("ResearchProgress", researchProgressList);

        if (this.hasCustomInventoryName()) {
            compound.setString("CustomName", name);
        }
    }

    private IResearchAnalysis getResearchAnalysis()
    {
        return ResearchRegistry.findMatchingAnalysis(Arrays.asList(inventory));
    }
}
