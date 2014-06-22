package com.ollieread.technomagi.event.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.event.TMEvent.ResearchCompleteEvent;
import com.ollieread.technomagi.api.event.TMEvent.ResearchProgressEvent;
import com.ollieread.technomagi.api.event.TMEvent.SpecialisationChosenEvent;
import com.ollieread.technomagi.api.research.ResearchEvents;
import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TMEventHandler
{

    @SubscribeEvent
    public void onSpecialisationChosen(SpecialisationChosenEvent event)
    {
        if (!event.entity.worldObj.isRemote) {
            PlayerKnowledge charon = PlayerKnowledge.get((EntityPlayer) event.entity);
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("Specialisation chosen: " + event.specialisation.getName()));

            TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_SPECIALISATION, event, charon);
        }
    }

    @SubscribeEvent
    public void onResearchComplete(ResearchCompleteEvent event)
    {
        if (!event.entity.worldObj.isRemote) {
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("Research Complete: " + event.research.getName()));
        }
    }

    @SubscribeEvent
    public void onResearchProgress(ResearchProgressEvent event)
    {
        if (!event.entity.worldObj.isRemote) {
            TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_RESEARCH_PROGRESS, event, event.charon);
        }
    }

}
