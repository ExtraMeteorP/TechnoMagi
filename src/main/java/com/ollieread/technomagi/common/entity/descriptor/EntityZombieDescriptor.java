package com.ollieread.technomagi.common.entity.descriptor;

import net.minecraft.entity.monster.EntityZombie;

import com.ollieread.technomagi.api.entity.EntityDescriptor;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityBrain;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityResearchNanites;

public class EntityZombieDescriptor extends EntityDescriptor implements IEntityBrain, IEntityResearchNanites
{

    public EntityZombieDescriptor()
    {
        super(EntityZombie.class, true);
    }

    public EntityZombieDescriptor(Class zombieClass)
    {
        super(zombieClass, true);
    }

    @Override
    public boolean isUndead()
    {
        return true;
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
        return 1;
    }

    @Override
    public int getExtractorDamage()
    {
        return 1;
    }

    @Override
    public int getMaxNanites()
    {
        return 100;
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
