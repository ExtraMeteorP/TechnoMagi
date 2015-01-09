package com.ollieread.technomagi.util;

import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import org.apache.commons.lang3.StringUtils;

import com.ollieread.ennds.research.IKnowledge;

public class EventHelper
{

    public static String knowledge(IKnowledge knowledge)
    {
        return "knowledge" + StringUtils.capitalize(StringUtils.replace(StringUtils.capitalize(knowledge.getName().replace('.', ' ')), " ", ""));
    }

    public static String damage(DamageSource damage, boolean entity)
    {
        if (damage.isExplosion()) {
            return "damageExplosion";
        }

        return "damage" + StringUtils.capitalize(damage.damageType);
    }

    public static String item(ItemStack stack)
    {
        return StringUtils.replace((StringUtils.capitalize((Item.itemRegistry.getNameForObject(stack.getItem())).replace('_', ' '))), " ", "");
    }

    public static String itemUse(ItemStack stack)
    {
        return (stack.getItem() instanceof ItemFood ? "eat" : "use") + item(stack);
    }

    public static String itemBroke(ItemStack stack)
    {
        return "broke" + item(stack);
    }

    public static String entity(Class entityClass, int type)
    {
        String entityName = (String) EntityList.classToStringMapping.get(entityClass);

        if (entityName != null) {
            switch (type) {
                case 0:
                    return entityName.toLowerCase() + "TargetedPlayer";
                case 1:
                    return entityName.toLowerCase() + "Passive";
                case 2:
                    return "attacked" + StringUtils.capitalize(entityName);
                case 3:
                    return "attackedBy" + StringUtils.capitalize(entityName);
                case 4:
                    return "killed" + StringUtils.capitalize(entityName);
                case 5:
                    return "breed" + StringUtils.capitalize(entityName);
                case 6:
                    return "birth" + StringUtils.capitalize(entityName);
                case 7:
                    return entityName.toLowerCase() + "BurningInSunlight";
            }
        }

        return null;
    }

    public static String entityTargeted(Class entityClass)
    {
        return entity(entityClass, 0);
    }

    public static String entityTargeted(Class entityClass, Class targetClass)
    {
        String targetName = (String) EntityList.classToStringMapping.get(targetClass);

        return StringUtils.replace(entity(entityClass, 0), "Player", targetName);
    }

    public static String entityPassive(Class entityClass)
    {
        return entity(entityClass, 1);
    }

    public static String entityAttacked(Class entityClass)
    {
        return entity(entityClass, 2);
    }

    public static String entityAttackedBy(Class entityClass)
    {
        return entity(entityClass, 3);
    }

    public static String entityKilled(Class entityClass)
    {
        return entity(entityClass, 4);
    }

    public static String entityBreed(Class entityClass)
    {
        return entity(entityClass, 5);
    }

    public static String entityBirth(Class entityClass)
    {
        return entity(entityClass, 6);
    }

    public static String entitySunlight(Class entityClass)
    {
        return entity(entityClass, 7);
    }

}
