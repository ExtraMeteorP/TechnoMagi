package com.ollieread.technomagi.specialisation;

import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.util.DamageSource;

import com.ollieread.ennds.Specialisation;
import com.ollieread.technomagi.common.Reference;

public class SpecialisationEngineer extends Specialisation
{

    public SpecialisationEngineer()
    {
        super("engineer", Reference.MODID.toLowerCase());
    }

    @Override
    public void applyAttributes(BaseAttributeMap map)
    {

    }

    @Override
    public int modifyDamage(DamageSource damage, int amount)
    {
        return amount;
    }

}
