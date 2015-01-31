package com.ollieread.technomagi.api.event;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.event.KnowledgeEvent.Chance;
import com.ollieread.technomagi.api.event.KnowledgeEvent.Progress;
import com.ollieread.technomagi.api.knowledge.research.IResearch;

public class TechnomagiHooks
{

    public static KnowledgeEvent.Chance researchChance(EntityPlayer entity, IResearch research, int chance)
    {
        Chance event = new Chance(entity, research, chance);

        TechnomagiApi.EVENT_BUS.post(event);

        return event;
    }

    public static KnowledgeEvent.Progress.Pre preKnowledgeProgress(EntityPlayer entity, IResearch research, int current, int progress)
    {
        Progress.Pre event = new Progress.Pre(entity, research, current, progress);

        TechnomagiApi.EVENT_BUS.post(event);

        return event;
    }

    public static void postKnowledgeProgress(EntityPlayer entity, IResearch research, int current, int progress)
    {
        TechnomagiApi.EVENT_BUS.post(new Progress.Post(entity, research, current, progress));
    }

}
