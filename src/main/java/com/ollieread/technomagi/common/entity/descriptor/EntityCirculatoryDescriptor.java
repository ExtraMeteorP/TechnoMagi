package com.ollieread.technomagi.common.entity.descriptor;

import net.minecraft.entity.EntityLivingBase;

import com.ollieread.technomagi.api.entity.EntityDescriptor;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityBrain;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityResearchNanites;

public class EntityCirculatoryDescriptor extends EntityDescriptor implements IEntityBrain, IEntityResearchNanites
{

    public EntityCirculatoryDescriptor(Class<? extends EntityLivingBase> entityClass, boolean isMonster)
    {
        super(entityClass, isMonster);
    }

    // Research Nanites Start

    @Override
    public float getSampleVolume()
    {
        return 0.8F;
    }

    @Override
    public int getSampleDamage()
    {
        return 2;
    }

    @Override
    public int getExtractorDamage()
    {
        return 2;
    }

    @Override
    public int getMaxNanites()
    {
        return 50;
    }

    @Override
    public float getNaniteRegen()
    {
        return 0.6F;
    }

    @Override
    public int getNaniteRegenTicks()
    {
        return 40;
    }

    @Override
    public int getMaxData()
    {
        return 50;
    }

    // Research Nanites End

    // Brain Start

    @Override
    public int getBrainMaxLife()
    {
        return 8400;
    }

    @Override
    public int getBrainPreservedMultiplier()
    {
        return 2;
    }

    @Override
    public int getBrainDropChance()
    {
        return 12;
    }

    // Brain End

}
