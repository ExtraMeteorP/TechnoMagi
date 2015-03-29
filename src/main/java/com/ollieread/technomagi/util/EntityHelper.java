package com.ollieread.technomagi.util;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
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

    public static boolean isStoodOn(EntityLivingBase entity, Block block)
    {
        return isStoodOn(entity, block, -1);
    }

    public static boolean isStoodOn(EntityLivingBase entity, Block block, int metadata)
    {
        if (getBlockStoodOn(entity) == block) {
            if (metadata == -1 || metadata == getBlockStoodOnMetadata(entity)) {
                return true;
            }
        }

        return false;
    }

    public static Block getBlockStoodOn(EntityLivingBase entity)
    {
        int x = MathHelper.floor_double(entity.posX);
        int y = MathHelper.floor_double(entity.posY) - 1;
        int z = MathHelper.floor_double(entity.posZ);

        return entity.worldObj.getBlock(x, y, z);
    }

    public static int getBlockStoodOnMetadata(EntityLivingBase entity)
    {
        int x = MathHelper.floor_double(entity.posX);
        int y = MathHelper.floor_double(entity.posY) - 1;
        int z = MathHelper.floor_double(entity.posZ);

        return entity.worldObj.getBlockMetadata(x, y, z);
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
