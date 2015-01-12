package com.ollieread.technomagi.ability.active;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityPayload;
import com.ollieread.ennds.ability.AbilityPayload.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

public class ActiveAbilityInvisibility extends AbilityActive
{

    protected int cost;
    protected int duration;

    public ActiveAbilityInvisibility(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        cost = Config.invisibilityCost;
        duration = Config.invisibilityDuration;
    }

    @Override
    public int getMaxFocus()
    {
        return 0;
    }

    @Override
    public boolean canIntervalFocus()
    {
        return false;
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, AbilityPayload cast)
    {
        return charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityPayload cast, ItemStack staff)
    {
        int level = 1;

        if (!charon.player.isPotionActive(Potion.invisibility)) {
            if (decreaseNanites(charon, cost)) {
                if (!charon.player.worldObj.isRemote) {
                    Random rand = new Random();

                    charon.player.addPotionEffect(new PotionEffect(Potion.invisibility.id, duration * level, level, false));
                }

                return true;
            }
        }

        return false;
    }

}
