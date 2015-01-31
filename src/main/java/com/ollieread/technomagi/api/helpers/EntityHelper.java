package com.ollieread.technomagi.api.helpers;

import java.util.Map;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.knowledge.PlayerKnowledge;
import com.ollieread.technomagi.api.nanites.PlayerNanites;

public class EntityHelper
{

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

    public static String getEntityName(Class entityClass)
    {
        Map mapping = EntityList.classToStringMapping;

        if (mapping.containsKey(entityClass)) {
            return (String) mapping.get(entityClass);
        }

        return null;
    }

    public static Class getEntityClass(String entityName)
    {
        Map mapping = EntityList.stringToClassMapping;

        if (mapping.containsKey(entityName)) {
            return (Class) mapping.get(entityName);
        }

        return null;
    }

}
