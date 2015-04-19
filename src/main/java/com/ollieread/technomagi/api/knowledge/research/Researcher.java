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
        String parent = research.getParent();

        if (!researchComplete.contains(name)) {
            boolean flag = (parent == null || parent.isEmpty()) || (parent != null && !parent.isEmpty() && researchComplete.contains(parent));

            if (flag) {
                if (repeat > 1) {
                    if (researchRepetition.containsKey(name)) {
                        int currentRepeat = researchRepetition.get(name);
                        currentRepeat++;

                        if (currentRepeat >= repeat) {
                            researchRepetition.remove(name);
                            researchComplete.add(name);
                        } else {
                            researchRepetition.put(name, currentRepeat);
                        }
                    } else {
                        researchRepetition.put(name, 1);
                    }
                } else {
                    researchComplete.add(name);
                }
            }

            return research.getProgress();
        }

        return 0;
    }

    @Override
    public void copyFrom(IResearcher research)
    {
        this.researchComplete = research.getCompleteResearch();
        this.researchRepetition = research.getResearchRepetition();
    }

    @Override
    public List<String> getCompleteResearch()
    {
        return this.researchComplete;
    }

    @Override
    public Map<String, Integer> getResearchRepetition()
    {
        return this.researchRepetition;
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
            NBTTagCompound c = completeResearch.getCompoundTagAt(i);
            this.researchComplete.add(c.getString("Research"));
        }

        /*
         * Research repetition
         */
        this.researchRepetition = new ConcurrentHashMap<String, Integer>();

        NBTTagList repeatResearch = compound.getTagList("ResearchRepeatition", compound.getId());

        for (int i = 0; i < repeatResearch.tagCount(); i++) {
            NBTTagCompound c = repeatResearch.getCompoundTagAt(i);
            this.researchRepetition.put(c.getString("Research"), c.getInteger("Repeatition"));
        }
    }

}
