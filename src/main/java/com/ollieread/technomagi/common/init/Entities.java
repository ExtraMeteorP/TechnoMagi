package com.ollieread.technomagi.common.init;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.common.entity.EntityRobotCow;
import com.ollieread.technomagi.common.entity.EntityRobotCreeper;
import com.ollieread.technomagi.common.entity.EntityRobotEnderman;
import com.ollieread.technomagi.common.entity.EntityRobotVillager;
import com.ollieread.technomagi.common.entity.EntityRobotZombie;
import com.ollieread.technomagi.common.entity.EntityZombieCow;
import com.ollieread.technomagi.common.entity.EntityZombieSheep;
import com.ollieread.technomagi.common.entity.descriptor.EntityCirculatoryDescriptor;
import com.ollieread.technomagi.common.entity.descriptor.EntityConstructDescriptor;
import com.ollieread.technomagi.common.entity.descriptor.EntityPlayerDescriptor;
import com.ollieread.technomagi.common.entity.descriptor.EntitySkeletonDescriptor;
import com.ollieread.technomagi.common.entity.descriptor.EntityZombieDescriptor;

import cpw.mods.fml.common.registry.EntityRegistry;

public class Entities
{

    public static void init()
    {
        EntityRegistry.registerModEntity(EntityZombieCow.class, "zombieCow", Config.zombieCow, Technomagi.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityZombieSheep.class, "zombieSheep", Config.zombieSheep, Technomagi.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityRobotCow.class, "robotCow", Config.robotCow, Technomagi.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityRobotCreeper.class, "robotCreeper", Config.robotCreeper, Technomagi.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityRobotZombie.class, "robotZombie", Config.robotZombie, Technomagi.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityRobotEnderman.class, "robotEnderman", Config.robotEnderman, Technomagi.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityRobotVillager.class, "robotVillager", Config.robotEnderman, Technomagi.instance, 80, 1, true);

        TechnomagiApi.entity().addEntity(new EntityZombieDescriptor());
        TechnomagiApi.entity().addEntity(new EntityPlayerDescriptor());
        TechnomagiApi.entity().addEntity(new EntitySkeletonDescriptor());

        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntitySpider.class, true));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntityCreeper.class, true));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntityWitch.class, true));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntityEnderman.class, true));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntitySilverfish.class, true));

        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntityWolf.class, false));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntitySheep.class, false));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntityCow.class, false));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntityPig.class, false));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntityChicken.class, false));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntityHorse.class, false));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntityIronGolem.class, false));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntityVillager.class, false));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntityOcelot.class, false));
        TechnomagiApi.entity().addEntity(new EntityCirculatoryDescriptor(EntityBat.class, false));

        TechnomagiApi.entity().addEntity(new EntityConstructDescriptor(EntitySlime.class, true));
        TechnomagiApi.entity().addEntity(new EntityConstructDescriptor(EntityMagmaCube.class, true));
        TechnomagiApi.entity().addEntity(new EntityConstructDescriptor(EntityBlaze.class, true));
    }

}
