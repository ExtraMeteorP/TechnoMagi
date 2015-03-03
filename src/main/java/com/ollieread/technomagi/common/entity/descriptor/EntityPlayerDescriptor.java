package com.ollieread.technomagi.common.entity.descriptor;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.entity.EntityDescriptor;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityResearchNanites;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntitySample;

public class EntityPlayerDescriptor extends EntityDescriptor implements IEntitySample, IEntityResearchNanites
{

    public EntityPlayerDescriptor()
    {
        super(EntityPlayer.class, false);
    }

    @Override
    public boolean canBeCaptured()
    {
        return false;
    }

    @Override
    public boolean canBeMonitored()
    {
        return false;
    }

    @Override
    public float getSampleVolume()
    {
        return 1F;
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
        return 0.25F;
    }

    @Override
    public int getNaniteRegenTicks()
    {
        return 40;
    }

    @Override
    public int getMaxData()
    {
        return 100;
    }

}
