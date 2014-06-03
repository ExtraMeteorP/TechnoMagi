package com.ollieread.technomagi.event.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.event.TMEvent.*;
import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TMEventHandler
{
	
	@SubscribeEvent
	public void onSpecialisationChosen(SpecialisationChosenEvent event)
	{ 	
		if(!event.entity.worldObj.isRemote) {
			PlayerKnowledge charon = PlayerKnowledge.get((EntityPlayer) event.entity);
			((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("Specialisation chosen: " + event.specialisation.getName()));
			
			TMRegistry.passiveAbilityEvent(TMRegistry.EVENT_SPECIALISATION, event, charon);
		}
	}
	
	@SubscribeEvent
	public void onResearchComplete(ResearchCompleteEvent event)
	{
		if(!event.entity.worldObj.isRemote) {
			((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("Research Complete: " + event.research.getName()));
		}
	}
	
	@SubscribeEvent
	public void onResearchProgress(ResearchProgressEvent event)
	{
		if(!event.entity.worldObj.isRemote) {
			TMRegistry.passiveAbilityEvent(TMRegistry.EVENT_RESEARCH_PROGRESS, event, event.charon);
		}
	}
	
}
