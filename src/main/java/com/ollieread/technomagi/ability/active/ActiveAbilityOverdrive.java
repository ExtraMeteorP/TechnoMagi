package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityCast;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

public class ActiveAbilityOverdrive extends AbilityActive
{

    protected Map<String, Integer> enhancements;
    protected int cost;
    protected int duration;

    public ActiveAbilityOverdrive(String name)
    {
        super(name, Reference.MODID.toLowerCase());
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("force", 2);
        this.cost = Config.overdriveCost;
        this.duration = Config.overdriveDuration;

        this.knowledge = new String[] { "motion" };
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
    public boolean canUse(ExtendedPlayerKnowledge charon, AbilityCast cast)
    {
        return charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityCast cast, ItemStack staff)
    {
        if (decreaseNanites(charon, cost)) {
            if (!charon.player.worldObj.isRemote) {
                Random rand = new Random();

                charon.player.addPotionEffect(new PotionEffect(Potion.jump.id, duration, 1, false));
                charon.player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, duration, 1, false));
            }

            return true;
        }

        return false;
    }

}
