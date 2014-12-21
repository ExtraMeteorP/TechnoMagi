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

public class ActiveAbilityInvisibility extends AbilityActive
{

    protected Map<String, Integer> enhancements;
    protected int cost;
    protected int duration;

    public ActiveAbilityInvisibility(String name)
    {
        super(name, Reference.MODID.toLowerCase());
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("light", 1);
        this.cost = Config.invisibilityCost;
        this.duration = Config.invisibilityDuration;

        this.knowledge = new String[] { "light" };
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
