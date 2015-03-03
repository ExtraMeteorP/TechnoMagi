package com.ollieread.technomagi.api.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityBrain;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityResearchNanites;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityRobot;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntitySample;
import com.ollieread.technomagi.util.EntityHelper;

/**
 * Entity Handler
 *
 * This handles the registration of entities. This does not replace normal
 * entity registration, this simply enables certain Technomagi specific features
 * for already registered entities.
 *
 * @author ollieread
 *
 */
public class EntityHandler
{

    protected Map<Class, IEntityDescriptor> entityList = new LinkedHashMap<Class, IEntityDescriptor>();
    protected List<Class> brainList = new ArrayList<Class>();
    protected List<Class> sampleList = new ArrayList<Class>();
    protected List<Class> naniteList = new ArrayList<Class>();
    protected List<Class> robotList = new ArrayList<Class>();

    /**
     * Register an entity for features.
     *
     * The entity descriptor should should by default extend
     * {@link IEntityDescriptor} and should extend one or more of the companion
     * interfaces.
     *
     * {@link IEntityBrain} for entities that should drop brains when killed.
     *
     * {@link IEntitySample} for entities that can have blood samples taken.
     * This is used for target nanites.
     *
     * {@link IEntityResearchNanites} for entities that can have their own
     * internal nanites, allowing them to be monitored and collect
     * data/research.
     *
     * {@link IEntityRobot} for entities that have a robotic counterpart.
     *
     * @param descriptor
     */
    public void addEntity(IEntityDescriptor descriptor)
    {
        if (!entityList.containsKey(descriptor.getEntityClass()) && !entityList.containsValue(descriptor)) {
            entityList.put(descriptor.getEntityClass(), descriptor);
            Class entityClass = descriptor.getEntityClass();

            if (descriptor instanceof IEntityBrain && !brainList.contains(entityClass)) {
                brainList.add(entityClass);
            }

            if (descriptor instanceof IEntitySample && !sampleList.contains(entityClass)) {
                sampleList.add(entityClass);
            }

            if (descriptor instanceof IEntityResearchNanites && !naniteList.contains(entityClass)) {
                naniteList.add(entityClass);
            }

            if (descriptor instanceof IEntityRobot && !robotList.contains(entityClass)) {
                robotList.add(entityClass);
            }
        }
    }

    public IEntityDescriptor getEntity(Class entityClass)
    {
        if (entityList.containsKey(entityClass)) {
            return entityList.get(entityClass);
        }

        return null;
    }

    public IEntityDescriptor getEntity(String entityName)
    {
        Class entityClass = null;

        if (entityName.equalsIgnoreCase("player")) {
            entityClass = EntityPlayer.class;
        } else {
            entityClass = EntityHelper.getEntityClass(entityName);
        }

        if (entityClass != null && entityList.containsKey(entityClass)) {
            return entityList.get(entityClass);
        }

        return null;
    }

    public Collection<IEntityDescriptor> getEntities()
    {
        return entityList.values();
    }

    public List<Class> getBrainableEntities()
    {
        return brainList;
    }

    public List<Class> getSampleableEntities()
    {
        return sampleList;
    }

    public List<Class> getNaniteEntities()
    {
        return naniteList;
    }

    public List<Class> getRobotEntities()
    {
        return robotList;
    }

    public static class DeadRegistry
    {

    }

}
