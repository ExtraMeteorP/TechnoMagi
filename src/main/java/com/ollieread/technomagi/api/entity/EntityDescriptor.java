package com.ollieread.technomagi.api.entity;

import net.minecraft.entity.EntityLivingBase;

/**
 * This is a base abstract class for simplicity, see {@link IEntityDescriptor}
 * for the companion classes and further information.
 *
 * @author ollieread
 *
 */
public class EntityDescriptor implements IEntityDescriptor
{

    protected Class<? extends EntityLivingBase> entityClass = null;
    protected boolean isMonster = false;

    public EntityDescriptor(Class<? extends EntityLivingBase> entityClass, boolean isMonster)
    {
        this.entityClass = entityClass;
        this.isMonster = isMonster;
    }

    @Override
    public Class<? extends EntityLivingBase> getEntityClass()
    {
        return entityClass;
    }

    @Override
    public boolean isMonster()
    {
        return isMonster;
    }

    @Override
    public boolean isUndead()
    {
        return false;
    }

    @Override
    public boolean canBeCaptured()
    {
        return true;
    }

    @Override
    public boolean canBeMonitored()
    {
        return true;
    }

    @Override
    public boolean canBeReanimated()
    {
        return false;
    }

    @Override
    public Class<? extends EntityLivingBase> getReanimatedEntityClass()
    {
        return null;
    }

}
