package com.ollieread.technomagi.ability.passive;

import net.minecraft.util.FoodStats;

import com.ollieread.ennds.ability.AbilityPassive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.ability.data.AbilityDataNanites;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PassiveAbilityNanites extends AbilityPassive<PlayerTickEvent>
{

    public PassiveAbilityNanites(String name)
    {
        super(name, Reference.MODID.toLowerCase());
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

        if (foodLevel >= 18 && charon.nanites.getNanites() < charon.nanites.getMaxNanites()) {
            AbilityDataNanites data = (AbilityDataNanites) charon.abilities.getData("naniteRegen");

            if (data == null) {
                data = new AbilityDataNanites();
                data.timer = 0;
            }

            ++data.timer;

            if (data.timer >= 80) {
                int nanites = charon.nanites.getNanites();
                charon.nanites.increaseNanites((int) Math.round(2 + ((nanites / 10) * 0.2)));
                data.timer = 0;
                charon.abilities.removeData(data);
            } else {
                charon.abilities.addData(data);
            }
        }
    }

}
