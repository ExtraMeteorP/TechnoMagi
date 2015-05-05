package com.ollieread.technomagi.common.ability.cast;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.ability.AbilityCast;
import com.ollieread.technomagi.api.ability.AbilityHandler.AbilityType;
import com.ollieread.technomagi.api.ability.AbilityPayload;
import com.ollieread.technomagi.api.ability.AbilityPayload.AbilityResult;
import com.ollieread.technomagi.api.ability.AbilityPayload.AbilityUseTarget;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.common.misc.PotionTechnomagi;

public class AbilityCastPhaseJump extends AbilityCast
{

    public AbilityCastPhaseJump(String name, ResourceLocation icon)
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
        if (abilityMode == 0) {
            return true;
        } else if (abilityMode == 1) {
            return payload.target.equals(AbilityUseTarget.ENTITY_LIVING);
        }

        return false;
    }

    @Override
    public void use(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode)
    {
        EntityPlayer player = technomage.getPlayer();

        if (technomage.nanites().decreaseNanites(10)) {
            player.addPotionEffect(new PotionEffect(PotionTechnomagi.phased.id, 600, 1));
            payload.setResult(AbilityResult.COMPLETE);
        } else {
            Technomagi.debug("Unable to decrease nanites");
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
        return abilityMode == 0 ? AbilityType.SELF : AbilityType.ENTITY_LIVING;
    }

    @Override
    public int getCooldown(int abilityMode)
    {
        return 80;
    }

    @Override
    public int getMaxFocus(int abilityMode)
    {
        return 20;
    }

}
