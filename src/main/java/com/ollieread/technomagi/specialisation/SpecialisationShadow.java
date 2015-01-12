package com.ollieread.technomagi.specialisation;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.util.DamageSource;

import com.ollieread.ennds.Specialisation;
import com.ollieread.technomagi.common.Reference;

public class SpecialisationShadow extends Specialisation
{

    public SpecialisationShadow()
    {
        super("shadow", Reference.MODID.toLowerCase());
    }

    @Override
    public void applyAttributes(BaseAttributeMap map)
    {
        map.getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(0.8999D);
    }

    @Override
    public int modifyDamage(DamageSource damage, int amount)
    {
        return amount;
    }

}
