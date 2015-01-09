package com.ollieread.technomagi.item.nanites;

import net.minecraft.entity.EntityLiving;

public interface INanites
{

    public Class getTargetEntity();

    public boolean hasEffect();

    public void applyAffects(EntityLiving entity);

}
