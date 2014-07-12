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

import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.entity.robot.EntityRobotCow;
import com.ollieread.technomagi.entity.robot.EntityRobotCreeper;
import com.ollieread.technomagi.entity.robot.EntityRobotZombie;
import com.ollieread.technomagi.util.EntityHelper;

import cpw.mods.fml.common.registry.EntityRegistry;

public class Entities
{

    public static int robotCowID;
    public static int robotCreeperID;
    public static int robotZombieID;

    public static void init()
    {
        robotCowID = EntityHelper.getUniqueEntityId();
        robotCreeperID = EntityHelper.getUniqueEntityId();
        robotZombieID = EntityHelper.getUniqueEntityId();

        EntityRegistry.registerModEntity(EntityRobotCow.class, "robotCow", robotCowID, TechnoMagi.instance, 80, 1, true);
        EntityList.addMapping(EntityRobotCow.class, "robotCow", robotCowID, 113213, 3523523);

        EntityRegistry.registerModEntity(EntityRobotCreeper.class, "robotCreeper", robotCreeperID, TechnoMagi.instance, 80, 1, true);
        EntityList.addMapping(EntityRobotCreeper.class, "robotCreeper", robotCreeperID, 113213, 3523523);

        EntityRegistry.registerModEntity(EntityRobotZombie.class, "robotZombie", robotZombieID, TechnoMagi.instance, 80, 1, true);
        EntityList.addMapping(EntityRobotZombie.class, "robotZombie", robotZombieID, 113213, 3523523);

        ResearchRegistry.registerEntity(EntityCow.class, true, robotCowID, false, true);
        ResearchRegistry.registerEntity(EntitySheep.class, false, 0, false, true);
        ResearchRegistry.registerEntity(EntityChicken.class, false, 0, false, true);
        ResearchRegistry.registerEntity(EntityHorse.class, false, 0, false, true);
        ResearchRegistry.registerEntity(EntityVillager.class, false, 0, true, true);

        ResearchRegistry.registerEntity(EntityCreeper.class, true, robotCreeperID, true, true);
        ResearchRegistry.registerEntity(EntityZombie.class, true, robotZombieID, true, true);
        ResearchRegistry.registerEntity(EntityPigZombie.class, false, 0, true, true);
        ResearchRegistry.registerEntity(EntitySkeleton.class, false, 0, true, true);
        ResearchRegistry.registerEntity(EntitySpider.class, false, 0, false, true);
        ResearchRegistry.registerEntity(EntityCaveSpider.class, false, 0, false, true);
        ResearchRegistry.registerEntity(EntityEnderman.class, false, 0, true, true);
    }

}
