package com.ollieread.technomagi.api.knowledge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.ollieread.technomagi.api.TechnoMagiApi;
import com.ollieread.technomagi.api.knowledge.research.IResearch;

public class PlayerKnowledge implements IExtendedEntityProperties
{

    protected EntityPlayer player;

    protected List<String> researchComplete = new ArrayList<String>();
    protected Map<String, Integer> researchRepeatition = new ConcurrentHashMap<String, Integer>();

    protected List<String> knowledgeComplete = new ArrayList<String>();
    protected Map<String, Integer> knowledgeProgress = new ConcurrentHashMap<String, Integer>();

    public PlayerKnowledge(EntityPlayer player)
    {
        this.player = player;
    }

    public static final void register(EntityPlayer player)
    {
        player.registerExtendedProperties(TechnoMagiApi.IDENT_KNOWLEDGE, new PlayerKnowledge(player));
    }

    public static final PlayerKnowledge get(EntityPlayer player)
    {
        return (PlayerKnowledge) player.getExtendedProperties(TechnoMagiApi.IDENT_KNOWLEDGE);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        /*
         * Research Complete
         */
        NBTTagList completeResearch = new NBTTagList();

        for (String research : researchComplete) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("Name", research);
            completeResearch.appendTag(tag);
        }

        compound.setTag("ResearchComplete", completeResearch);

        /*
         * Research repeatition
         */
        NBTTagList repeatResearch = new NBTTagList();

        for (Entry<String, Integer> entry : researchRepeatition.entrySet()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("Research", entry.getKey());
            tag.setInteger("Repeatition", entry.getValue());
            repeatResearch.appendTag(tag);
        }

        compound.setTag("ResearchRepeatition", repeatResearch);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {

    }

    @Override
    public void init(Entity entity, World world)
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

    public boolean addResearch(IResearch research)
    {
        String name = research.getName();
        int repeat = research.getRepeatition();

        if (!researchComplete.contains(name)) {
            if (repeat > 1) {
                if (researchRepeatition.containsKey(name)) {
                    int currentRepeat = researchRepeatition.get(name);
                    currentRepeat++;

                    if (currentRepeat == repeat) {
                        researchRepeatition.remove(name);
                        researchComplete.add(name);
                    } else {
                        researchRepeatition.put(name, currentRepeat);
                    }
                }
            } else {
                researchComplete.add(name);
            }

            return true;
        }

        return false;
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

    public boolean addKnowledge(String knowledge)
    {
        if (!knowledgeComplete.contains(knowledge)) {
            knowledgeComplete.add(knowledge);

            return true;
        }

        return false;
    }

}
