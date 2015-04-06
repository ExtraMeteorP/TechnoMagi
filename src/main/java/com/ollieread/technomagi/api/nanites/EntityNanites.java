package com.ollieread.technomagi.api.nanites;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.IResearch;
import com.ollieread.technomagi.api.knowledge.research.Researcher;

import cpw.mods.fml.relauncher.Side;

public class EntityNanites extends Researcher
{

    protected String owner = "";

    protected boolean player = false;
    protected boolean active = false;
    protected int nanites = 0;
    protected int data = 0;
    protected int maxData = 0;
    protected int maxNanites = 0;
    protected float regenMultiplier = 0F;
    protected int regenTicks = -1;

    protected Map<String, Integer> knowledgeProgress = new ConcurrentHashMap<String, Integer>();

    protected NaniteRegen regen;

    private Random rand = new Random();

    public EntityNanites(boolean isPlayer)
    {
        this.player = isPlayer;
        this.regen = new NaniteRegen();
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

        if (this.player) {
            ((PlayerNanites) this).sync();
        }
    }

    /**
     *
     * @param nanites
     */
    public void setNanites(int nanites)
    {
        this.nanites = nanites;

        if (this.player) {
            ((PlayerNanites) this).sync();
        }
    }

    /**
     *
     * @param max
     */
    public void setMaxData(int max)
    {
        this.maxData = max;

        if (this.player) {
            ((PlayerNanites) this).sync();
        }
    }

    /**
     *
     * @param max
     */
    public void setMaxNanites(int max)
    {
        this.maxNanites = max;

        if (this.player) {
            ((PlayerNanites) this).sync();
        }
    }

    public void setRegen(float regen)
    {
        this.regenMultiplier = regen;

        if (this.player) {
            ((PlayerNanites) this).sync();
        }
    }

    public void setRegenTicks(int ticks)
    {
        this.regenTicks = ticks;

        if (this.player) {
            ((PlayerNanites) this).sync();
        }
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
            setKnowledgeProgress(knowledge, progress);

            if (this.player) {
                ((PlayerNanites) this).sync();
            }
        } else {
            int current = knowledgeProgress.get(knowledge);
            current += progress;

            setKnowledgeProgress(knowledge, current);

            if (this.player) {
                ((PlayerNanites) this).sync();
            }

            return true;
        }

        return false;
    }

    public void setKnowledgeProgress(String knowledge, int progress)
    {
        if (progress <= 0) {
            knowledgeProgress.remove(knowledge);
        } else {
            knowledgeProgress.put(knowledge, progress);
        }
    }

    public int getKnowledgeProgress(String knowledge)
    {
        if (knowledgeProgress.containsKey(knowledge)) {
            return knowledgeProgress.get(knowledge);
        }

        return 0;
    }

    public Map<String, Integer> getKnowledgeProgress()
    {
        return knowledgeProgress;
    }

    /**
     * Perform a piece of research.
     *
     * This checks to make sure that the knowledge hasn't already been
     * discovered, the research hasn't already been performed and whether or not
     * the research has been performed the maximum amount of times. It will also
     * automatically add knowledge progress.
     *
     * @param technomage
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
        super.saveNBTData(compound);

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
         * Knowledge progress
         */
        NBTTagList progressKnowledge = new NBTTagList();

        for (Entry<String, Integer> entry : this.knowledgeProgress.entrySet()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("Knowledge", entry.getKey());
            tag.setInteger("Progress", entry.getValue());
            progressKnowledge.appendTag(tag);
        }

        compound.setTag("KnowledgeProgress", progressKnowledge);

        this.regen.saveNBTData(compound);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        super.loadNBTData(compound);

        this.active = compound.getBoolean("Active");

        /**
         * Owner, this is only used for non player entities.
         */
        if (!player) {
            this.owner = compound.getString("Owner");
        }

        /**
         * Nanites
         */
        this.maxNanites = compound.getInteger("MaxNanites");
        this.nanites = compound.getInteger("Nanites");

        /**
         * Data
         */
        this.maxData = compound.getInteger("MaxData");
        this.data = compound.getInteger("Data");

        /*
         * Knowledge progress
         */
        this.knowledgeProgress = new ConcurrentHashMap<String, Integer>();

        NBTTagList progressKnowledge = compound.getTagList("KnowledgeProgress", compound.getId());

        for (int i = 0; i < progressKnowledge.tagCount(); i++) {
            NBTTagCompound c = progressKnowledge.getCompoundTagAt(i);
            String knowledge = c.getString("Knowledge");
            int progress = c.getInteger("Progress");

            if (knowledge != null && progress > 0) {
                this.knowledgeProgress.put(knowledge, progress);
            }
        }

        this.regen.loadNBTData(compound);
    }

    public void update(Side side)
    {
        if (side.equals(Side.SERVER)) {
            if (active) {
                this.regen.regen(this.regenTicks, this.regenMultiplier, this);
            }
        }
    }

    public void sync()
    {
        if (this.player) {
            ((PlayerNanites) this).sync();
        } else {

        }
    }

}
