package com.ollieread.technomagi.api.nanites;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityResearchNanites;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.knowledge.research.IResearch;
import com.ollieread.technomagi.common.network.PacketHandler;
import com.ollieread.technomagi.common.network.packets.MessageSyncPlayerNanites;

public class PlayerNanites extends EntityNanites
{

    protected PlayerTechnomagi technomage;

    public PlayerNanites(PlayerTechnomagi technomagi)
    {
        super(true);
        this.technomage = technomagi;
        IEntityResearchNanites descriptor = (IEntityResearchNanites) TechnomagiApi.entity().getEntity(EntityPlayer.class);
        this.maxData = descriptor.getMaxData();
        this.maxNanites = descriptor.getMaxNanites();
        this.regenMultiplier = descriptor.getNaniteRegen();
        this.regenTicks = descriptor.getNaniteRegenTicks();
    }

    @Override
    public boolean canResearch(IResearch research)
    {
        if (technomage.knowledge().canDiscover(TechnomagiApi.getKnowledge(research.getKnowledge())) && !researchComplete.contains(research.getName())) {
            return true;
        }

        return false;
    }

    @Override
    public void sync()
    {
        EntityPlayer player = this.technomage.getPlayer();

        if (!player.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendTo(new MessageSyncPlayerNanites(player), (EntityPlayerMP) player);
        }
    }

    @Override
    public boolean addKnowledgeProgress(String knowledge, int progress)
    {
        if (!technomage.knowledge().hasKnowledge(knowledge)) {
            if (!knowledgeProgress.containsKey(knowledge)) {
                knowledgeProgress.put(knowledge, progress);
            } else {
                int current = knowledgeProgress.get(knowledge);
                current += progress;

                if (current >= 100) {
                    knowledgeProgress.remove(knowledge);
                    technomage.knowledge().addKnowledge(knowledge);
                    technomage.sync();

                    return true;
                } else {
                    knowledgeProgress.put(knowledge, current);
                    technomage.sync();

                    return true;
                }
            }
        }

        return false;
    }

}
