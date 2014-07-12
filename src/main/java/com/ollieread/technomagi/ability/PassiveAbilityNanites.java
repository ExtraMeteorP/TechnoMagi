package com.ollieread.technomagi.ability;

import net.minecraft.util.FoodStats;

import com.ollieread.ennds.ability.AbilityPassive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PassiveAbilityNanites extends AbilityPassive<PlayerTickEvent>
{

    private int timer;

    public PassiveAbilityNanites(String name)
    {
        super(name);
    }

    @Override
    public String getEvent()
    {
        return "playerTick";
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
