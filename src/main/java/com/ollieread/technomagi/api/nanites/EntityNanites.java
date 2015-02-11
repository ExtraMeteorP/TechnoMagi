package com.ollieread.technomagi.api.nanites;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.IResearch;
import com.ollieread.technomagi.api.knowledge.research.Researcher;

public class EntityNanites extends Researcher
{

    protected String owner = "";

    protected boolean player = false;
    protected boolean active = false;
    protected int nanites = 0;
    protected int data = 0;
    protected int maxData = 100;
    protected int maxNanites = 100;

    protected List<String> researchComplete = new ArrayList<String>();
    protected Map<String, Integer> researchRepetition = new ConcurrentHashMap<String, Integer>();
    protected Map<String, Integer> knowledgeProgress = new ConcurrentHashMap<String, Integer>();

    private Random rand = new Random();

    public EntityNanites(boolean isPlayer)
    {
        this.player = isPlayer;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public boolean getActive()
    {
        return this.active;
    }

    public String getOwner()
    {
        return this.owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public boolean hasOwner()
    {
        return owner != null && !owner.isEmpty();
    }

    /**
     * 
     * @return
     */
    public int getData()
    {
        return this.data;
    }

    /**
     * 
     * @return
     */
    public int getNanites()
    {
        return this.nanites;
    }

    /**
     * 
     * @return
     */
    public int getMaxData()
    {
        return this.maxData;
    }

    /**
     * 
     * @return
     */
    public int getMaxNanites()
    {
        return this.maxNanites;
    }

    /**
     * 
     * @param data
     */
    public void setData(int data)
    {
        this.data = data;
    }

    /**
     * 
     * @param nanites
     */
    public void setNanites(int nanites)
    {
        this.nanites = nanites;
    }

    /**
     * 
     * @param max
     */
    public void setMaxData(int max)
    {
        this.maxData = max;
    }

    /**
     * 
     * @param max
     */
    public void setMaxNanites(int max)
    {
        this.maxNanites = max;
    }

    /**
     * 
     * @param data
     * @return
     */
    public boolean increaseData(int data)
    {
        int newData = this.data + data;

        if (newData <= this.maxData) {
            setData(newData);
            return true;
        }

        return false;
    }

    /**
     * 
     * @param data
     * @return
     */
    public boolean decreaseData(int data)
    {
        int newData = this.data - data;

        if (newData >= 0) {
            setData(newData);
            return true;
        }

        return false;
    }

    /**
     * 
     * @param nanites
     * @return
     */
    public boolean increaseNanites(int nanites)
    {
        int newNanites = this.nanites + nanites;

        if (newNanites <= this.maxNanites) {
            setNanites(newNanites);
            return true;
        }

        return false;
    }

    /**
     * 
     * @param nanites
     * @return
     */
    public boolean decreaseNanites(int nanites)
    {
        int newNanites = this.nanites - nanites;

        if (newNanites >= 0) {
            setNanites(newNanites);
            return true;
        }

        return false;
    }

    /**
     * 
     * @param data
     * @return
     */
    public boolean canIncreaseData(int data)
    {
        int newData = this.data + data;

        if (newData <= this.maxData) {
            return true;
        }

        return false;
    }

    /**
     * 
     * @param data
     * @return
     */
    public boolean canDecreaseData(int data)
    {
        int newData = this.data - data;

        if (newData >= 0) {
            return true;
        }

        return false;
    }

    /**
     * 
     * @param nanites
     * @return
     */
    public boolean canIncreaseNanites(int nanites)
    {
        int newNanites = this.nanites + nanites;

        if (newNanites <= this.maxNanites) {
            return true;
        }

        return false;
    }

    /**
     * 
     * @param nanites
     * @return
     */
    public boolean canDecreaseNanites(int nanites)
    {
        int newNanites = this.nanites - nanites;

        if (newNanites >= 0) {
            return true;
        }

        return false;
    }

    /**
     * 
     * @param knowledge
     * @param progress
     * @return
     */
    public boolean addKnowledgeProgress(String knowledge, int progress)
    {
        if (!knowledgeProgress.containsKey(knowledge)) {
            knowledgeProgress.put(knowledge, progress);
        } else {
            int current = knowledgeProgress.get(knowledge);
            current += progress;

            knowledgeProgress.put(knowledge, current);

            return true;
        }

        return false;
    }

    /**
     * Perform a piece of research.
     * 
     * This checks to make sure that the knowledge hasn't already been
     * discovered, the research hasn't already been performed and whether or not
     * the research has been performed the maximum amount of times. It will also
     * automatically add knowledge progress.
     * 
     * @param technomagi
     * @param research
     */
    public void performResearch(IResearch research, Knowledge knowledge)
    {
        // Verify that the knowledge and research is usable
        if (canResearch(research)) {
            int chance = research.getChance();

            if (rand.nextInt(research.getChance()) == 0) {
                int progress = addResearch(research, 0);

                if (progress > 0) {
                    // TODO Add pre knowledge progress event

                    if (addKnowledgeProgress(knowledge.getName(), progress)) {
                        // TODO Add post knowledge progress event
                    }
                }
            }
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        compound.setBoolean("Active", this.active);

        /**
         * Owner, this is only used for non player entities.
         */
        if (!player) {
            compound.setString("Owner", owner);
        }

        /**
         * Nanites
         */
        compound.setInteger("MaxNanites", this.maxNanites);
        compound.setInteger("Nanites", this.nanites);

        /**
         * Data
         */
        compound.setInteger("MaxData", this.maxData);
        compound.setInteger("Data", this.data);

        /*
         * Research Complete
         */
        NBTTagList completeResearch = new NBTTagList();

        for (String research : this.researchComplete) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("Research", research);
            completeResearch.appendTag(tag);
        }

        compound.setTag("ResearchComplete", completeResearch);

        /*
         * Research repetition
         */
        NBTTagList repeatResearch = new NBTTagList();

        for (Entry<String, Integer> entry : this.researchRepetition.entrySet()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("Research", entry.getKey());
            tag.setInteger("Repeatition", entry.getValue());
            repeatResearch.appendTag(tag);
        }

        compound.setTag("ResearchRepeatition", repeatResearch);

        /*
         * Knowledge progress
         */
        NBTTagList progressKnowledge = new NBTTagList();

        for (Entry<String, Integer> entry : this.knowledgeProgress.entrySet()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("Knowledge", entry.getKey());
            tag.setInteger("Progress", entry.getValue());
            repeatResearch.appendTag(tag);
        }

        compound.setTag("KnowledgeProgress", progressKnowledge);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        /*
         * Research Complete
         */
        this.researchComplete = new ArrayList<String>();

        NBTTagList completeResearch = compound.getTagList("ResearchComplete", compound.getId());

        for (int i = 0; i < completeResearch.tagCount(); i++) {
            this.researchComplete.add(compound.getString("Research"));
        }

        /*
         * Research repetition
         */
        this.researchRepetition = new ConcurrentHashMap<String, Integer>();

        NBTTagList repeatResearch = compound.getTagList("ResearchRepeatition", compound.getId());

        for (int i = 0; i < repeatResearch.tagCount(); i++) {
            this.researchRepetition.put(compound.getString("Research"), compound.getInteger("Repeatition"));
        }

        /*
         * Knowledge progress
         */
        this.knowledgeProgress = new ConcurrentHashMap<String, Integer>();

        NBTTagList progressKnowledge = compound.getTagList("KnowledgeProgress", compound.getId());

        for (int i = 0; i < repeatResearch.tagCount(); i++) {
            this.knowledgeProgress.put(compound.getString("Knowledge"), compound.getInteger("Progress"));
        }
    }

}
