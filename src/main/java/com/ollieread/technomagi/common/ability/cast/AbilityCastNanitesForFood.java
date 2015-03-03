package com.ollieread.technomagi.common.ability.cast;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.ability.AbilityCast;
import com.ollieread.technomagi.api.ability.AbilityHandler.AbilityType;
import com.ollieread.technomagi.api.ability.AbilityPayload;
import com.ollieread.technomagi.api.ability.AbilityPayload.AbilityResult;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;

public class AbilityCastNanitesForFood extends AbilityCast
{

    public AbilityCastNanitesForFood(String name, ResourceLocation icon)
    {
        super(name, icon);
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
        if (technomage.nanites().decreaseNanites(10)) {
            technomage.getPlayer().getFoodStats().addStats(2, 0.3F);
            payload.setResult(AbilityResult.COMPLETE);
        } else {
            payload.setResult(AbilityResult.HALT);
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

}
