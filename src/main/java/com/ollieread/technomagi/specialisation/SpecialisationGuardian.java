package com.ollieread.technomagi.specialisation;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.util.DamageSource;

import com.ollieread.ennds.Specialisation;
import com.ollieread.technomagi.common.Reference;

public class SpecialisationGuardian extends Specialisation
{

    public SpecialisationGuardian()
    {
        super("guardian", Reference.MODID.toLowerCase());
    }

    @Override
    public void applyAttributes(BaseAttributeMap map)
    {
        map.getAttributeInstance(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
    }

    @Override
    public int modifyDamage(DamageSource damage, int amount)
    {
        return amount;
    }

}
