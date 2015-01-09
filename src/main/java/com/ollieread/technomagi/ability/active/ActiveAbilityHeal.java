package com.ollieread.technomagi.ability.active;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityCast;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseTarget;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

public class ActiveAbilityHeal extends AbilityActive
{

    protected int cost;
    protected int duration;

    public ActiveAbilityHeal(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        cost = Config.healCost;
        duration = Config.healDuration;
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
        if (charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH)) {
            if (cast.target.equals(AbilityUseTarget.ENTITY_LIVING) || cast.target.equals(AbilityUseTarget.PLAYER)) {
                return charon.isSpecialisation("medic");
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityCast cast, ItemStack staff)
    {
        Random rand = new Random();
        int level = 3;

        if (cast.target.equals(AbilityUseTarget.BLOCK) || cast.target.equals(AbilityUseTarget.AIR)) {
            if (decreaseNanites(charon, cost)) {
                if (!charon.player.worldObj.isRemote) {
                    charon.player.addPotionEffect(new PotionEffect(Potion.regeneration.id, duration, level - 1));
                }

                return true;
            }
        } else if (cast.target.equals(AbilityUseTarget.ENTITY_LIVING)) {
            if (charon.isSpecialisation("medic")) {
                EntityLiving entity = (EntityLiving) cast.targetEntityLiving;
                if (decreaseNanites(charon, cost)) {
                    if (!charon.player.worldObj.isRemote) {
                        entity.addPotionEffect(new PotionEffect(Potion.regeneration.id, duration, level - 1));
                    }

                    return true;
                }
            }
        } else if (cast.target.equals(AbilityUseTarget.PLAYER)) {
            if (charon.isSpecialisation("medic")) {
                EntityPlayer entity = (EntityPlayer) cast.targetPlayer;
                if (decreaseNanites(charon, cost)) {
                    if (!charon.player.worldObj.isRemote) {
                        entity.addPotionEffect(new PotionEffect(Potion.regeneration.id, duration, level - 1));
                    }

                    return true;
                }
            }
        }

        return false;
    }

}
