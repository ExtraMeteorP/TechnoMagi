package com.ollieread.technomagi.common.entity.descriptor;

import net.minecraft.entity.monster.EntitySkeleton;

import com.ollieread.technomagi.api.entity.EntityDescriptor;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityBrain;

public class EntitySkeletonDescriptor extends EntityDescriptor implements IEntityBrain
{

    public EntitySkeletonDescriptor()
    {
        super(EntitySkeleton.class, true);
    }

    @Override
    public boolean isUndead()
    {
        return true;
    }

    // Brain Start

    @Override
    public int getBrainMaxLife()
    {
        return 4200;
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
