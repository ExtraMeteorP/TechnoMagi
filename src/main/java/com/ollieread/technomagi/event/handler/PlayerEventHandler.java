package com.ollieread.technomagi.event.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageAbility;
import com.ollieread.technomagi.player.PlayerAbilities;
import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventHandler
{
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{ 
		if(event.entity instanceof EntityPlayer) {
			if(PlayerKnowledge.get((EntityPlayer) event.entity) == null) {
				PlayerKnowledge.register((EntityPlayer) event.entity);
			}
			if(PlayerAbilities.get((EntityPlayer) event.entity) == null) {
				PlayerAbilities.register((EntityPlayer) event.entity);
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			PlayerKnowledge.saveProxyData((EntityPlayer) event.entity);
			PlayerAbilities.saveProxyData((EntityPlayer) event.entity);
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			PlayerKnowledge.loadProxyData((EntityPlayer) event.entity);
			PlayerAbilities.loadProxyData((EntityPlayer) event.entity);
		}
	}
	
	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event)
	{
		if(event.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_AIR)) {
			EntityPlayer player = event.entityPlayer;
			
			//Charon.get((EntityPlayer) event.entity).useAbility();			
			//TechnoMagi.packetPipeline.sendToServer(new UseCurrentAbilityPacket());
			PacketHandler.INSTANCE.sendToServer(new MessageAbility(3));
		}
	}
	
	@SubscribeEvent
	public void onEntityHurt(LivingHurtEvent event)
	{
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			if(event.source.equals(DamageSource.inFire)) {
				TMRegistry.passiveAbilityEvent(TMRegistry.EVENT_IN_FIRE, event, PlayerKnowledge.get((EntityPlayer) event.entity));
				TMRegistry.researchEvent(TMRegistry.EVENT_IN_FIRE, event, PlayerKnowledge.get((EntityPlayer) event.entity));
			}
		}
	}
	
}
