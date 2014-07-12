package com.ollieread.technomagi.event.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import com.ollieread.ennds.ability.AbilityRegistry;
import com.ollieread.ennds.event.EnndsEvent.ResearchCompleteEvent;
import com.ollieread.ennds.event.EnndsEvent.ResearchProgressEvent;
import com.ollieread.ennds.event.EnndsEvent.SpecialisationChosenEvent;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TMEventHandler
{

    @SubscribeEvent
    public void onSpecialisationChosen(SpecialisationChosenEvent event)
    {
        if (!event.entity.worldObj.isRemote) {
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get((EntityPlayer) event.entity);
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("Specialisation chosen: " + event.specialisation.getName()));

            AbilityRegistry.passiveAbilityEvent("specialisation", event, charon);
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
            AbilityRegistry.passiveAbilityEvent("researchProgress", event, event.charon);
        }
    }

}
