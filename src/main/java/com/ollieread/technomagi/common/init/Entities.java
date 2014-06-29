package com.ollieread.technomagi.common.init;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.entity.robot.EntityRobotCow;
import com.ollieread.technomagi.entity.robot.EntityRobotCreeper;
import com.ollieread.technomagi.util.EntityHelper;

import cpw.mods.fml.common.registry.EntityRegistry;

public class Entities
{

    public static int robotCowID;
    public static int robotCreeperID;

    public static void init()
    {
        robotCowID = EntityHelper.getUniqueEntityId();
        robotCreeperID = EntityHelper.getUniqueEntityId();

        EntityRegistry.registerModEntity(EntityRobotCow.class, "robotCow", robotCowID, TechnoMagi.instance, 80, 1, true);
        EntityList.addMapping(EntityRobotCow.class, "robotCow", robotCowID, 113213, 3523523);

        EntityRegistry.registerModEntity(EntityRobotCreeper.class, "robotCreeper", robotCreeperID, TechnoMagi.instance, 80, 1, true);
        EntityList.addMapping(EntityRobotCreeper.class, "robotCreeper", robotCreeperID, 113213, 3523523);

        TMRegistry.registerEntity(EntityCow.class, true, robotCowID, false, true);
        TMRegistry.registerEntity(EntitySheep.class, false, 0, false, true);
        TMRegistry.registerEntity(EntityChicken.class, false, 0, false, true);
        TMRegistry.registerEntity(EntityHorse.class, false, 0, false, true);
        TMRegistry.registerEntity(EntityVillager.class, false, 0, true, true);

        TMRegistry.registerEntity(EntityCreeper.class, true, robotCreeperID, true, true);
        TMRegistry.registerEntity(EntityZombie.class, false, 0, true, true);
        TMRegistry.registerEntity(EntityPigZombie.class, false, 0, true, true);
        TMRegistry.registerEntity(EntitySkeleton.class, false, 0, true, true);
        TMRegistry.registerEntity(EntitySpider.class, false, 0, false, true);
        TMRegistry.registerEntity(EntityCaveSpider.class, false, 0, false, true);
        TMRegistry.registerEntity(EntityEnderman.class, false, 0, true, true);
    }

}
