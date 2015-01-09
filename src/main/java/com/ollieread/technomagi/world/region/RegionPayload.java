package com.ollieread.technomagi.world.region;

import net.minecraft.entity.EntityLivingBase;
import cpw.mods.fml.common.eventhandler.Event;

public class RegionPayload<E>
{

    public EntityLivingBase entity;
    public EntityLivingBase targetEntity;
    public Event event;

    public RegionPayload(EntityLivingBase entity, EntityLivingBase targetEntity, Event event)
    {
        this.entity = entity;
        this.targetEntity = targetEntity;
        this.event = event;
    }

    public E getEvent()
    {
        return (E) event;
    }

}
