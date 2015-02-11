package com.ollieread.technomagi.api.knowledge.research;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class Researcher implements IResearcher
{

    protected List<String> researchComplete = new ArrayList<String>();
    protected Map<String, Integer> researchRepetition = new ConcurrentHashMap<String, Integer>();

    @Override
    public boolean canResearch(IResearch research)
    {
        if (!researchComplete.contains(research.getName())) {
            return true;
        }

        return false;
    }

    @Override
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
    }

}
