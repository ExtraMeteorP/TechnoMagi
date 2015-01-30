package com.ollieread.technomagi.api.event;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.TechnoMagiApi;
import com.ollieread.technomagi.api.event.TechnoMagiEvent.ResearchChanceEvent;
import com.ollieread.technomagi.api.knowledge.research.IResearch;

public class TechnoMagiHooks
{

    public static ResearchChanceEvent researchChance(EntityPlayer entity, IResearch research, int chance)
    {
        ResearchChanceEvent event = new ResearchChanceEvent(entity, research, chance);

        TechnoMagiApi.EVENT_BUS.post(event);

        return event;
    }

}
