package com.ollieread.technomagi.api.knowledge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.technomagi.api.knowledge.research.IResearch;

public class PlayerKnowledge
{

    protected List<String> researchComplete = new ArrayList<String>();
    protected Map<String, Integer> researchRepetition = new ConcurrentHashMap<String, Integer>();

    protected List<String> knowledgeComplete = new ArrayList<String>();
    protected Map<String, Integer> knowledgeProgress = new ConcurrentHashMap<String, Integer>();

    private Random rand = new Random();

    public PlayerKnowledge()
    {
    }

    public boolean canResearch(IResearch research)
    {
        if (!knowledgeComplete.contains(research.getKnowledge())) {
            if (!researchComplete.contains(research.getName())) {
                return true;
            }
        }

        return false;
    }

    public int addResearch(IResearch research, int modifier)
    {
        String name = research.getName();
        int repeat = research.getRepetition();

        if (!researchComplete.contains(name)) {
            if (repeat > 1) {
                if (researchRepetition.containsKey(name)) {
                    int currentRepeat = researchRepetition.get(name);
                    currentRepeat++;

                    if (currentRepeat == repeat) {
                        researchRepetition.remove(name);
                        researchComplete.add(name);
                    } else {
                        researchRepetition.put(name, currentRepeat);
                    }
                }
            } else {
                researchComplete.add(name);
            }

            return research.getProgress();
        }

        return 0;
    }

    public boolean canDiscover(Knowledge knowledge)
    {
        if (!knowledgeComplete.contains(knowledge.getName())) {
            List<String> prerequisites = knowledge.getPrerequisites();

            if (prerequisites != null) {
                for (String prerequisite : prerequisites) {
                    if (!knowledgeComplete.contains(prerequisite)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean addKnowledgeProgress(String knowledge, int progress)
    {
        if (!knowledgeComplete.contains(knowledge)) {
            if (!knowledgeProgress.containsKey(knowledge)) {
                knowledgeProgress.put(knowledge, progress);
            } else {
                int current = knowledgeProgress.get(knowledge);
                current += progress;

                if (current >= 100) {
                    knowledgeProgress.remove(knowledge);
                    addKnowledge(knowledge);

                    return true;
                } else {
                    knowledgeProgress.put(knowledge, current);

                    return true;
                }
            }
        }

        return false;
    }

    public int getKnowledgeProgress(String knowledge)
    {
        if (!knowledgeComplete.contains(knowledge)) {
            if (knowledgeProgress.containsKey(knowledge)) {
                return knowledgeProgress.get(knowledge);
            }
        } else {
            return 100;
        }

        return 0;
    }

    public boolean addKnowledge(String knowledge)
    {
        if (!knowledgeComplete.contains(knowledge)) {
            knowledgeComplete.add(knowledge);

            return true;
        }

        return false;
    }

    public void saveNBTData(NBTTagCompound compound)
    {
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
         * Knowledge Complete
         */
        NBTTagList completeKnowledge = new NBTTagList();

        for (String research : this.knowledgeComplete) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("Knowledge", research);
            completeResearch.appendTag(tag);
        }

        compound.setTag("KnowledgeComplete", completeKnowledge);

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
         * Knowledge Complete
         */
        this.knowledgeComplete = new ArrayList<String>();

        NBTTagList completeKnowledge = compound.getTagList("KnowledgeComplete", compound.getId());

        for (int i = 0; i < completeKnowledge.tagCount(); i++) {
            this.knowledgeComplete.add(compound.getString("Knowledge"));
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
