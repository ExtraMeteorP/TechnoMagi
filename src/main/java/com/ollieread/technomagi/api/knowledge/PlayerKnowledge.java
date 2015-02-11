package com.ollieread.technomagi.api.knowledge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.knowledge.research.IResearch;
import com.ollieread.technomagi.api.knowledge.research.Researcher;

public class PlayerKnowledge extends Researcher
{

    protected PlayerTechnomagi technomagi;

    protected List<String> knowledgeComplete = new ArrayList<String>();
    protected Map<String, Integer> knowledgeProgress = new ConcurrentHashMap<String, Integer>();

    private Random rand = new Random();

    public PlayerKnowledge(PlayerTechnomagi technomagi)
    {
        this.technomagi = technomagi;
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

    @Override
    public boolean canResearch(IResearch research)
    {
        if (canDiscover(TechnomagiApi.getKnowledge(research.getKnowledge())) && !researchComplete.contains(research.getName())) {
            return true;
        }

        return false;
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

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        super.saveNBTData(compound);

        /*
         * Knowledge Complete
         */
        NBTTagList completeKnowledge = new NBTTagList();

        for (String research : this.knowledgeComplete) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("Knowledge", research);
            completeKnowledge.appendTag(tag);
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
            progressKnowledge.appendTag(tag);
        }

        compound.setTag("KnowledgeProgress", progressKnowledge);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        super.loadNBTData(compound);

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

        for (int i = 0; i < progressKnowledge.tagCount(); i++) {
            this.knowledgeProgress.put(compound.getString("Knowledge"), compound.getInteger("Progress"));
        }
    }

}
