package com.ollieread.technomagi.event.handler;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.player.PlayerKnowledge;

import net.minecraft.util.FoodStats;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class TickEventHandler {
	
	@SubscribeEvent
	public void onPlayerTickEvent(PlayerTickEvent event)
	{
		if(!event.player.worldObj.isRemote) {
			TMRegistry.passiveAbilityEvent(TMRegistry.EVENT_PLAYER_TICK, event, PlayerKnowledge.get(event.player));
		}
	}

}
