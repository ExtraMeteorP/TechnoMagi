package com.ollieread.technomagi.util;

import java.util.Map;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.EntityTechnomagi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;

public class EntityHelper
{

    /**
     * Get the entity name for the class, from EntityList.
     *
     * @param entityClass
     * @return
     */
    public static String getEntityName(Class entityClass)
    {
        Map mapping = EntityList.classToStringMapping;

        if (mapping.containsKey(entityClass)) {
            return (String) mapping.get(entityClass);
        }

        return null;
    }

    /**
     * Get the class for the entity name, from EntityList.
     *
     * @param entityName
     * @return
     */
    public static Class getEntityClass(String entityName)
    {
        Map mapping = EntityList.stringToClassMapping;

        if (mapping.containsKey(entityName)) {
            return (Class) mapping.get(entityName);
        }

        return null;
    }

    /**
     * Whether or not the entity has samples.
     *
     * @param entityClass
     * @return
     */
    public static boolean hasSample(Class entityClass)
    {
        return TechnomagiApi.entity().getSampleableEntities().contains(entityClass);
    }

    /**
     * Whether or not the entity drops a brain.
     *
     * @param entityClass
     * @return
     */
    public static boolean hasBrain(Class entityClass)
    {
        return TechnomagiApi.entity().getBrainableEntities().contains(entityClass);
    }

    /**
     * Whether or not the entity can be captured.
     *
     * @param entityClass
     * @return
     */
    public static boolean canBeCaptured(Class entityClass)
    {
        IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(entityClass);

        if (descriptor != null) {
            return descriptor.canBeCaptured();
        }

        return false;
    }

    public static EntityTechnomagi getTechnomagi(EntityLivingBase entity)
    {
        return EntityTechnomagi.get(entity);
    }

    public static boolean canSeeBlock(EntityLivingBase entity, int x, int y, int z)
    {
        return entity.worldObj.rayTraceBlocks(Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), Vec3.createVectorHelper(x, y, z)) == null;
    }

    public static class EntitySelectorPotion implements IEntitySelector
    {

        private int potionId;
        private boolean with;

        public EntitySelectorPotion(int id, boolean with)
        {
            this.potionId = id;
            this.with = with;
        }

        @Override
        public boolean isEntityApplicable(Entity entity)
        {
            return entity instanceof EntityLivingBase && with ? ((EntityLivingBase) entity).isPotionActive(potionId) : !((EntityLivingBase) entity).isPotionActive(potionId);
        }

    };

}
