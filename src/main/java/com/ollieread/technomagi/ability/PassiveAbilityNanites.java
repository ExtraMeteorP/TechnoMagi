package com.ollieread.technomagi.ability;

import net.minecraft.util.FoodStats;

import com.ollieread.technomagi.api.ability.AbilityPassive;
import com.ollieread.technomagi.api.research.ResearchEvents;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PassiveAbilityNanites extends AbilityPassive<PlayerTickEvent>
{

    private int timer;

    public PassiveAbilityNanites(String name)
    {
        super(name);
    }

    @Override
    public int getEvent()
    {
        return ResearchEvents.EVENT_PLAYER_TICK;
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon)
    {
        return charon.nanites.getNanites() < charon.nanites.getMaxNanites();
    }

    @Override
    public boolean isAvailable(ExtendedPlayerKnowledge charon)
    {
        return charon.canSpecialise() == false;
    }

    @Override
    public void use(PlayerTickEvent event, ExtendedPlayerKnowledge charon)
    {
        FoodStats foodstats = event.player.getFoodStats();
        int foodLevel = foodstats.getFoodLevel();

        if (charon.abilities.getResetTimer() == true) {
            timer = 0;
            charon.abilities.setResetTimer(false);
            return;
        }

        if (foodLevel >= 18) {
            ++timer;

            if (timer >= 80) {
                int nanites = charon.nanites.getNanites();
                charon.nanites.increaseNanites((int) Math.round(2 + ((nanites / 10) * 0.2)));
                timer = 0;
            }
        }
    }
}
