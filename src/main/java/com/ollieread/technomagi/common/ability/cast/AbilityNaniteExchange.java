package com.ollieread.technomagi.common.ability.cast;

import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.ability.AbilityCast;
import com.ollieread.technomagi.api.ability.AbilityHandler.AbilityType;
import com.ollieread.technomagi.api.ability.AbilityPayload;
import com.ollieread.technomagi.api.ability.AbilityPayload.AbilityResult;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.util.ResourceHelper;

public class AbilityNaniteExchange extends AbilityCast
{

    public AbilityNaniteExchange(String name)
    {
        super(name, null);
    }

    @Override
    public boolean isAvailable(PlayerTechnomagi technomage)
    {
        return true;
    }

    @Override
    public boolean canUse(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode)
    {
        return technomage.getPlayer().getFoodStats().needFood();
    }

    @Override
    public void use(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode)
    {
        if (abilityMode == 1 || abilityMode == 3) {
            if (technomage.nanites().decreaseNanites(10)) {
                if (abilityMode == 1) {
                    technomage.getPlayer().getFoodStats().addStats(2, 0.3F);
                } else if (abilityMode == 3) {
                    technomage.getPlayer().heal(2F);
                }
                payload.setResult(AbilityResult.COMPLETE);
            } else {
                payload.setResult(AbilityResult.HALT);
            }
        } else {
            if (abilityMode == 0) {
                technomage.getPlayer().getFoodStats().setFoodLevel(technomage.getPlayer().getFoodStats().getFoodLevel() - 2);

                if (technomage.nanites().increaseNanites(10)) {
                    payload.setResult(AbilityResult.COMPLETE);
                } else {
                    payload.setResult(AbilityResult.HALT);
                }
            } else if (abilityMode == 2) {
                technomage.getPlayer().attackEntityFrom(DamageSource.generic, 2F);

                if (technomage.nanites().increaseNanites(10)) {
                    payload.setResult(AbilityResult.COMPLETE);
                } else {
                    payload.setResult(AbilityResult.HALT);
                }
            }
        }
    }

    @Override
    public void stoppedUsing(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode)
    {
    }

    @Override
    public AbilityType getType(int abilityMode)
    {
        return AbilityType.SELF;
    }

    @Override
    public int getCooldown(int abilityMode)
    {
        return 80;
    }

    @Override
    public int cycleMode(PlayerTechnomagi technomage, int abilityMode)
    {
        abilityMode++;
        return abilityMode > 3 ? 0 : abilityMode;
    }

    @Override
    public ResourceLocation getIcon(int abilityMode)
    {
        switch (abilityMode) {
            case 1:
                return ResourceHelper.texture("abilities/nanites_food.png");
            case 2:
                return ResourceHelper.texture("abilities/health_nanites.png");
            case 3:
                return ResourceHelper.texture("abilities/nanites_health.png");
            default:
                return ResourceHelper.texture("abilities/food_nanites.png");
        }
    }

    @Override
    public String getUnlocalisedName(int abilityMode)
    {
        String modeName = "";

        switch (abilityMode) {
            case 1:
                modeName = "nanites_food";
            case 2:
                modeName = "health_nanites";
            case 3:
                modeName = "nanites_health";
            default:
                modeName = "food_nanites";
        }

        return "ability.cast." + name + "." + modeName;
    }

}
