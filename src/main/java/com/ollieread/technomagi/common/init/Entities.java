package com.ollieread.technomagi.common.init;

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
import com.ollieread.technomagi.entity.robot.EntityRobotEnderman;
import com.ollieread.technomagi.entity.robot.EntityRobotVillager;
import com.ollieread.technomagi.entity.robot.EntityRobotZombie;

import cpw.mods.fml.common.registry.EntityRegistry;

public class Entities
{

    public static void init()
    {

        EntityRegistry.registerModEntity(EntityRobotCow.class, "robotCow", Config.robotCow, TechnoMagi.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityRobotCreeper.class, "robotCreeper", Config.robotCreeper, TechnoMagi.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityRobotZombie.class, "robotZombie", Config.robotZombie, TechnoMagi.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityRobotEnderman.class, "robotEnderman", Config.robotEnderman, TechnoMagi.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityRobotVillager.class, "robotVillager", Config.robotEnderman, TechnoMagi.instance, 80, 1, true);

        ResearchRegistry.registerEntity(EntityCow.class, true, EntityRobotCow.class, false, true);
        ResearchRegistry.registerEntity(EntitySheep.class, false, null, false, true);
        ResearchRegistry.registerEntity(EntityChicken.class, false, null, false, true);
        ResearchRegistry.registerEntity(EntityHorse.class, false, null, false, true);
        ResearchRegistry.registerEntity(EntityVillager.class, true, EntityRobotVillager.class, true, true);
        ResearchRegistry.registerEntity(EntityCreeper.class, true, EntityRobotCreeper.class, true, true);
        ResearchRegistry.registerEntity(EntityZombie.class, true, EntityRobotZombie.class, true, true);
        ResearchRegistry.registerEntity(EntityPigZombie.class, false, null, true, true);
        ResearchRegistry.registerEntity(EntitySkeleton.class, false, null, true, true);
        ResearchRegistry.registerEntity(EntitySpider.class, false, null, false, true);
        ResearchRegistry.registerEntity(EntityCaveSpider.class, false, null, false, true);
        ResearchRegistry.registerEntity(EntityEnderman.class, true, EntityRobotEnderman.class, true, true);
    }

}
