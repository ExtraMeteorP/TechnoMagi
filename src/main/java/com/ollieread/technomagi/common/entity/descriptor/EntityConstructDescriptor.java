package com.ollieread.technomagi.common.entity.descriptor;

import net.minecraft.entity.EntityLivingBase;

import com.ollieread.technomagi.api.entity.EntityDescriptor;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityBrain;

public class EntityConstructDescriptor extends EntityDescriptor implements IEntityBrain
{

    public EntityConstructDescriptor(Class<? extends EntityLivingBase> entityClass, boolean isMonster)
    {
        super(entityClass, isMonster);
    }

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
