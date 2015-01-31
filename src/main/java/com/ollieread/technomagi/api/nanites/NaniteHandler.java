package com.ollieread.technomagi.api.nanites;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;

public class NaniteHandler
{

    protected List<Class<? extends EntityLiving>> entityList = new ArrayList<Class<? extends EntityLiving>>();

    public void addEntity(Class<? extends EntityLiving> entityClass)
    {
        if (entityList.contains(entityClass)) {
            entityList.add(entityClass);
        }
    }

}
