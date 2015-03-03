package com.ollieread.technomagi.common.entity.descriptor;

import net.minecraft.entity.EntityLivingBase;

import com.ollieread.technomagi.api.entity.EntityDescriptor;
import com.ollieread.technomagi.api.entity.EntityRobot;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityBrain;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityResearchNanites;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityRobot;

public class EntityCirculatoryRobotDescriptor extends EntityDescriptor implements IEntityBrain, IEntityResearchNanites, IEntityRobot
{

    protected Class<? extends EntityRobot> robotClass;

    public EntityCirculatoryRobotDescriptor(Class<? extends EntityLivingBase> entityClass, boolean isMonster, Class<? extends EntityRobot> robotClass)
    {
        super(entityClass, isMonster);

        this.robotClass = robotClass;
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

    // Robot Start

    @Override
    public Class<? extends EntityRobot> getRobotClass()
    {
        return robotClass;
    }

    // Robot End

}
