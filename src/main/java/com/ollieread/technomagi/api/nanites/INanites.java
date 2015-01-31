package com.ollieread.technomagi.api.nanites;

import net.minecraft.entity.EntityLivingBase;

public interface INanites
{

    public Class<? extends EntityLivingBase> getEntity();

    public int getMaxNanites();

    public int getRegenTicks();

    public int getRegenAmount();

    public boolean isMonster();

    public boolean isDead();

}
