package com.ollieread.technomagi.api.knowledge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.event.TechnomagiHooks;
import com.ollieread.technomagi.api.knowledge.research.Researcher;
import com.ollieread.technomagi.common.network.PacketHandler;
import com.ollieread.technomagi.common.network.packets.MessageSyncPlayerKnowledge;

import cpw.mods.fml.relauncher.Side;

public class PlayerKnowledge extends Researcher
{

    protected PlayerTechnomagi technomage;

    protected List<String> knowledgeComplete = new ArrayList<String>();
    protected Map<String, Integer> knowledgeProgress = new ConcurrentHashMap<String, Integer>();

    protected boolean syncing = false;
    protected int syncTicks = 0;

    private Random rand = new Random();

    public PlayerKnowledge(PlayerTechnomagi technomagi)
    {
        this.technomage = technomagi;
    }

    public boolean canDiscover(Knowledge knowledge)
    {
        if (!knowledgeComplete.contains(knowledge.getName())) {
            String prerequisite = knowledge.getPrerequisite();

            if (prerequisite != null) {
                if (!knowledgeComplete.contains(prerequisite)) {
                    return false;
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
        } else {
            Technomagi.debug("Complete Knowledge: " + knowledgeComplete);
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

    public boolean hasKnowledge(String knowledge)
    {
        return knowledgeComplete.contains(knowledge);
    }

    public boolean hasKnowledge(String[] knowledge)
    {
        for (int i = 0; i < knowledge.length; i++) {
            if (!hasKnowledge(knowledge[i])) {
                return false;
            }
        }

        return true;
    }

    public boolean addKnowledge(String knowledge)
    {
        if (!knowledgeComplete.contains(knowledge)) {
            knowledgeComplete.add(knowledge);
            TechnomagiHooks.unlockedKnowledge(technomage.getPlayer(), TechnomagiApi.getKnowledge(knowledge));
            sync();

            return true;
        }

        return false;
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        super.saveNBTData(compound);

        compound.setBoolean("Syncing", syncing);
        compound.setInteger("SyncTicks", syncTicks);

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

        syncing = compound.getBoolean("Syncing");
        syncTicks = compound.getInteger("SyncTicks");

        /*
         * Knowledge Complete
         */
        this.knowledgeComplete = new ArrayList<String>();

        NBTTagList completeKnowledge = compound.getTagList("KnowledgeComplete", compound.getId());

        for (int i = 0; i < completeKnowledge.tagCount(); i++) {
            NBTTagCompound c = completeKnowledge.getCompoundTagAt(i);
            this.knowledgeComplete.add(c.getString("Knowledge"));
        }

        /*
         * Knowledge progress
         */
        this.knowledgeProgress = new ConcurrentHashMap<String, Integer>();

        NBTTagList progressKnowledge = compound.getTagList("KnowledgeProgress", compound.getId());

        for (int i = 0; i < progressKnowledge.tagCount(); i++) {
            NBTTagCompound c = progressKnowledge.getCompoundTagAt(i);
            this.knowledgeProgress.put(c.getString("Knowledge"), c.getInteger("Progress"));
        }
    }

    public void sync()
    {
        EntityPlayer player = this.technomage.getPlayer();

        if (!player.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendTo(new MessageSyncPlayerKnowledge(player), (EntityPlayerMP) player);
        }
    }

    public boolean isSyncing()
    {
        return syncing;
    }

    public void toggleSyncing()
    {
        if (syncing) {
            syncing = false;
        } else {
            syncing = true;
        }
    }

    public void update(Side side)
    {
        if (!technomage.getPlayer().worldObj.isRemote) {

            if (syncing) {
                Map<String, Integer> kp = technomage.nanites().getKnowledgeProgress();

                if (kp != null && kp.size() > 0) {
                    syncTicks++;

                    if (syncTicks >= 40) {
                        for (Entry<String, Integer> entry : kp.entrySet()) {
                            String knowledge = entry.getKey();
                            int progress = entry.getValue();

                            if (knowledge == null) {
                                break;
                            }

                            if (progress >= 5) {
                                if (addKnowledgeProgress(knowledge, 5)) {
                                    Technomagi.debug("Knowledge Progress: " + knowledgeProgress);
                                    Technomagi.debug("Adding 5 progress for " + knowledge);
                                    technomage.nanites().setKnowledgeProgress(knowledge, progress - 5);
                                    technomage.nanites().decreaseData(5);
                                    syncTicks = 0;
                                    technomage.sync();
                                    return;
                                }
                            } else {
                                if (addKnowledgeProgress(knowledge, progress)) {
                                    Technomagi.debug("Knowledge Progress: " + knowledgeProgress);
                                    Technomagi.debug("Adding " + progress + " progress for " + knowledge);
                                    technomage.nanites().setKnowledgeProgress(knowledge, progress);
                                    technomage.nanites().decreaseData(progress);
                                    syncTicks = 0;
                                    technomage.sync();
                                    return;
                                }
                            }
                        }
                    } else {
                        return;
                    }
                }

                syncing = false;
                syncTicks = 0;
                // technomage.nanites().setData(0);
                technomage.sync();
            }
        }
    }

}
