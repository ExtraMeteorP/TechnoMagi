package com.ollieread.technomagi.ability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.EnumDifficulty;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.ability.AbilityPassive;
import com.ollieread.technomagi.api.event.TMEvent.ResearchProgressEvent;
import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PassiveAbilityNanites extends AbilityPassive<PlayerTickEvent> {
	
	private int timer;

	public PassiveAbilityNanites(String name)
	{
		super(name);
	}

	@Override
	public int getEvent()
	{
		return TMRegistry.EVENT_PLAYER_TICK;
	}

	@Override
	public boolean canUse(PlayerKnowledge charon)
	{
		return charon.getNanites() < charon.getMaxNanites();
	}

	@Override
	public boolean isAvailable(PlayerKnowledge charon)
	{		
		return !charon.canSpecialise();
	}

	@Override
	public void use(PlayerTickEvent event, PlayerKnowledge charon)
	{
		FoodStats foodstats = event.player.getFoodStats();
		int foodLevel = foodstats.getFoodLevel();
		
		if(foodLevel >= 18) {
            ++timer;

            if(timer >= 80) {
            	charon.increaseNanites(5);
                timer = 0;
            }
        }
	}

}
