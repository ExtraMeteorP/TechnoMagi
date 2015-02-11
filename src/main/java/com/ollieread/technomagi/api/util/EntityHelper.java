package com.ollieread.technomagi.api.util;

import java.util.Map;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.knowledge.PlayerKnowledge;
import com.ollieread.technomagi.api.nanites.PlayerNanites;

public class EntityHelper
{

    /**
     * Get the Technomagi instance for the player.
     * 
     * @param player
     * @return
     */
    public static PlayerTechnomagi getPlayerTechnomagi(EntityPlayer player)
    {
        return PlayerTechnomagi.get(player);
    }

    public static PlayerKnowledge getPlayerKnowledge(EntityPlayer player)
    {
        return getPlayerTechnomagi(player).knowledge();
    }

    public static PlayerNanites getPlayerNanites(EntityPlayer player)
    {
        return getPlayerTechnomagi(player).nanites();
    }

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

}
