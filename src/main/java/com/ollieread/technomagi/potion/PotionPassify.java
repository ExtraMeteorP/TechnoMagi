package com.ollieread.technomagi.potion;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEnderman;

public class PotionPassify extends PotionTM
{

    public PotionPassify()
    {
        super("potion.passify", false, 8171462);

        addModifier(SharedMonsterAttributes.movementSpeed, -0.15000000596046448D, 2);
        setIconIndex(0, 0);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int effect)
    {
        if (entity instanceof EntityLiving) {
            ((EntityLiving) entity).setAttackTarget(null);
        }

        if (entity instanceof EntityEnderman) {
            ((EntityEnderman) entity).setScreaming(false);
        }

        entity.setRevengeTarget(null);
    }

}
