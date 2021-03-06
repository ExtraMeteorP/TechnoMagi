package com.ollieread.technomagi.ability.active;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityPayload;
import com.ollieread.ennds.ability.AbilityPayload.AbilityUseTarget;
import com.ollieread.ennds.ability.AbilityPayload.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

public class ActiveAbilityShield extends AbilityActive
{

    protected int cost;
    protected int duration;

    public ActiveAbilityShield(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        cost = Config.shieldCost;
        duration = Config.shieldDuration;
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
        if (charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH)) {
            if (cast.target.equals(AbilityUseTarget.ENTITY_LIVING) || cast.target.equals(AbilityUseTarget.PLAYER)) {
                return charon.isSpecialisation("medic");
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityPayload cast, ItemStack staff)
    {
        Random rand = new Random();
        int level = 3;

        if (!cast.target.equals(AbilityUseTarget.ENTITY_LIVING) && !cast.target.equals(AbilityUseTarget.PLAYER)) {
            if (decreaseNanites(charon, 10)) {
                if (!charon.player.worldObj.isRemote) {
                    charon.player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, duration * (level - 1), level - 2));
                }

                return true;
            }
        } else {
            EntityLivingBase entity = null;

            if (cast.target.equals(AbilityUseTarget.ENTITY_LIVING)) {
                entity = cast.targetEntityLiving;
            } else {
                entity = cast.targetPlayer;
            }

            if (entity != null && entity instanceof EntityLivingBase) {
                if (charon.isSpecialisation("medic")) {
                    if (decreaseNanites(charon, cost)) {
                        if (!charon.player.worldObj.isRemote) {
                            entity.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, duration * (level - 1), level - 2));
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

}
