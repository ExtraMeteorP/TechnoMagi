package com.ollieread.technomagi.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import org.apache.commons.lang3.StringUtils;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;

public class EventHelper
{

    public static void findAndFireForPlayers(String event, World world, int x, int y, int z)
    {
        List<EntityPlayer> players = world.playerEntities;

        for (EntityPlayer player : players) {
            if (PlayerHelper.canSeeBlock(player, x, y, z)) {
                ExtendedPlayerKnowledge playerKnowledge = PlayerHelper.getPlayerKnowledge(player);

                if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
                    ResearchRegistry.researchEvent(event, null, playerKnowledge, true);
                }
            }
        }
    }

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

    public static String damageProjectile(EntityDamageSourceIndirect damage)
    {
        Entity source = damage.getSourceOfDamage();
        return damageProjectile(source.getClass());
    }

    public static String damageProjectile(Class damage)
    {
        String entityName = (String) EntityList.classToStringMapping.get(damage);

        return "damage" + StringUtils.capitalize(entityName);
    }

    public static String item(ItemStack stack)
    {
        String name = stack.getUnlocalizedName().replaceFirst("tile.", "").replaceFirst("item.", "");
        return StringUtils.replace((StringUtils.capitalize(name.replace('_', ' '))), " ", "");
    }

    public static String itemUse(ItemStack stack)
    {
        return itemUse(stack, false);
    }

    public static String itemUse(ItemStack stack, boolean plant)
    {
        Item item = stack.getItem();
        String prefix = "use";

        if (plant && item instanceof IPlantable) {
            prefix = "plant";
        } else if (item instanceof ItemFood) {
            prefix = "eat";
        } else if (item instanceof ItemBlock) {
            prefix = "place";
        }

        return prefix + item(stack);
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
                case 8:
                    return "tempt" + StringUtils.capitalize(entityName);
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

        return StringUtils.replace(entity(entityClass, 0), "Player", StringUtils.capitalize(targetName));
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

    public static String entityTempt(Class entityClass)
    {
        return entity(entityClass, 8);
    }
}
