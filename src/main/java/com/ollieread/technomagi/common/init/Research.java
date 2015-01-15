package com.ollieread.technomagi.common.init;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import com.ollieread.ennds.EnndsRegistry;
import com.ollieread.technomagi.util.DamageSourceTM;
import com.ollieread.technomagi.util.EventHelper;
import com.ollieread.technomagi.util.ItemHelper;

public class Research
{

    public static String OBSIDIAN = "lavaCreatedObsidian";
    public static String COBBLESTONE = "lavaCreatedCobblestone";
    public static String PORTAL_NETHER = "createdNetherPortal";
    public static String PORTAL_END = "fixedEndPortal";

    public static void init()
    {
        EnndsRegistry.registerEvent("specialisation");
        EnndsRegistry.registerEvent("knowledgeProgress");
        EnndsRegistry.registerEvent("playerTick");
        EnndsRegistry.registerEvent("shearedSheep");
        EnndsRegistry.registerEvent("milkCow");
        EnndsRegistry.registerEvent("toNether");
        EnndsRegistry.registerEvent("toEnd");
        EnndsRegistry.registerEvent("toOverworld");
        EnndsRegistry.registerEvent("playerChangedDimension");
        EnndsRegistry.registerEvent("enderTeleport");
        EnndsRegistry.registerEvent("enderTeleportEnderman");
        EnndsRegistry.registerEvent("sleep");
        EnndsRegistry.registerEvent("casting");
        EnndsRegistry.registerEvent("crafting");
        EnndsRegistry.registerEvent("smelting");
        EnndsRegistry.registerEvent("lavaCreatedObsidian");
        EnndsRegistry.registerEvent("lavaCreatedCobblestone");
        EnndsRegistry.registerEvent("createdNetherPortal");
        EnndsRegistry.registerEvent("fixedEndPortal");

        // raw food
        addItem(ItemHelper.item("porkchop"), false);
        addItem(ItemHelper.item("beef"), false);
        addItem(ItemHelper.item("chicken"), false);
        addItem(ItemHelper.item("fish"), false);
        addItem(ItemHelper.item("apple"), false);
        addItem(ItemHelper.item("potato"), true);
        addItem(ItemHelper.item("carrot"), true);
        addItem(ItemHelper.item("melon"), false);
        addItem(ItemHelper.item("rotten_flesh"), false);
        addItem(ItemHelper.item("spider_eye"), false);
        addItem(ItemHelper.item("poisonous_potato"), false);
        // prepared food
        addItem(ItemHelper.item("cooked_porkchop"), false);
        addItem(ItemHelper.item("cooked_beef"), false);
        addItem(ItemHelper.item("cooked_chicken"), false);
        addItem(ItemHelper.item("cooked_fished"), false);
        addItem(ItemHelper.item("mushroom_stew"), false);
        addItem(ItemHelper.item("baked_potato"), false);
        addItem(ItemHelper.item("pumpkin_pie"), false);
        addItem(ItemHelper.item("bread"), false);
        addItem(ItemHelper.item("golden_apple"), false);
        addItem(ItemHelper.item("golden_carrot"), false);
        // seeds
        addItem(ItemHelper.item("wheat_seeds"), true);
        addItem(ItemHelper.item("pumpkin_seeds"), true);
        addItem(ItemHelper.item("melon_seeds"), true);
        // throwable
        addItem(ItemHelper.item("ender_pearl"), false);
        addItem(ItemHelper.item("egg"), false);
        addItem(ItemHelper.item("snowball"), false);
        // useable
        addItem(ItemHelper.item("flint_and_steel"), false);
        addItem(ItemHelper.item("flint_and_steel"), false);

        addEntity(EntityCreeper.class, true, false);
        addEntity(EntitySkeleton.class, true, false);
        addEntity(EntitySpider.class, true, false);
        addEntity(EntityZombie.class, true, false);
        addEntity(EntitySlime.class, true, false);
        addEntity(EntityGhast.class, true, false);
        addEntity(EntityPigZombie.class, true, false);
        addEntity(EntityEnderman.class, true, false);
        addEntity(EntityCaveSpider.class, true, false);
        addEntity(EntitySilverfish.class, true, false);
        addEntity(EntityBlaze.class, true, false);
        addEntity(EntityMagmaCube.class, true, false);
        addEntity(EntityIronGolem.class, true, false);
        addEntity(EntitySnowman.class, true, false);
        addEntity(EntityWolf.class, true, false);
        addEntity(EntityWitch.class, true, false);
        addEntity(EntityBat.class, false, false);
        addEntity(EntityPig.class, false, true);
        addEntity(EntitySheep.class, false, true);
        addEntity(EntityCow.class, false, true);
        addEntity(EntityChicken.class, false, true);
        addEntity(EntitySquid.class, false, false);
        addEntity(EntityMooshroom.class, false, true);
        addEntity(EntityOcelot.class, false, true);
        addEntity(EntityHorse.class, false, true);
        addEntity(EntityVillager.class, false, false);

        addDamage(DamageSource.anvil);
        addDamage(DamageSource.cactus);
        addDamage(DamageSource.drown);
        addDamage(DamageSource.fall);
        addDamage(DamageSource.fallingBlock);
        addDamage(DamageSource.generic);
        addDamage(DamageSource.inFire);
        addDamage(DamageSource.inWall);
        addDamage(DamageSource.lava);
        addDamage(DamageSource.magic);
        addDamage(DamageSource.onFire);
        addDamage(DamageSource.outOfWorld);
        addDamage(DamageSource.starve);
        addDamage(DamageSource.wither);
        addDamage((new DamageSource("explosion")).setExplosion());
        addDamage(DamageSourceTM.voidDamage);
    }

    public static void addItem(ItemStack stack, boolean plant)
    {
        EnndsRegistry.registerEvent(EventHelper.itemUse(stack));

        if (plant) {
            EnndsRegistry.registerEvent(EventHelper.itemUse(stack, true));
        }
    }

    public static void addEntity(Class entityClass, boolean hostile, boolean breed)
    {
        EnndsRegistry.registerEvent(EventHelper.entityAttacked(entityClass));
        EnndsRegistry.registerEvent(EventHelper.entityKilled(entityClass));

        if (hostile) {
            EnndsRegistry.registerEvent(EventHelper.entityAttackedBy(entityClass));
        }

        if (breed) {
            EnndsRegistry.registerEvent(EventHelper.entityBreed(entityClass));
            EnndsRegistry.registerEvent(EventHelper.entityBirth(entityClass));
            EnndsRegistry.registerEvent(EventHelper.entityTempt(entityClass));
        }
    }

    public static void addDamage(DamageSource damage)
    {
        EnndsRegistry.registerEvent(EventHelper.damage(damage, false));
    }
}
