package com.ollieread.technomagi.common.init;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config
{

    public static Configuration config;

    public static boolean versionCheck;

    public static boolean atmosphereEnabled;
    public static int atmosphereCost;
    public static int atmosphereDuration;
    public static boolean blinkEnabled;
    public static int blinkCost;
    public static int blinkDistance;
    public static boolean fireEnabled;
    public static int fireCost;
    public static int fireExtinguishCost;
    public static int fireEntityCost;
    public static int fireBurnTime;
    public static boolean fireballEnabled;
    public static int fireballCost;
    public static boolean flashstepEnabled;
    public static int flashstepCost;
    public static int flashstepDistance;
    public static boolean forceEnabled;
    public static int forceCost;
    public static boolean hardenEnabled;
    public static int hardenCost;
    public static boolean harvestEnabled;
    public static int harvestCost;
    public static boolean healEnabled;
    public static int healCost;
    public static boolean invisibilityEnabled;
    public static int invisibilityCost;
    public static int invisibilityDuration;
    public static boolean projectileEnabled;
    public static int projectileCost;
    public static boolean projectileExothermicEnabled;
    public static int projectileExothermicCost;
    public static boolean reactiveEnabled;
    public static int reactiveCost;

    public static int furnacePowerMax;
    public static int furnacePowerReceive;
    public static int furnacePowerUse;
    public static int furnaceProgressMax;
    public static int separatorPowerMax;
    public static int separatorPowerRecieve;
    public static int separatorPowerUse;
    public static int separatorProgressMax;
    public static int focuserPowerMax;
    public static int focuserPowerRecieve;
    public static int focuserPowerUse;
    public static int focuserProgressMax;
    public static int focuserMaxChargers;
    public static int encouragerPowerMax;
    public static int encouragerPowerRecieve;
    public static int encouragerPowerUse;
    public static int encouragerProgressMax;
    public static int analysisPowerMax;
    public static int analysisPowerRecieve;
    public static int analysisPowerUse;
    public static int analysisProgressMax;
    public static int analysisWaitingMax;
    public static int analysisTicksMax;
    public static int craftingPowerMax;
    public static int craftingPowerRecieve;
    public static int craftingPowerUse;
    public static int craftingProgressMax;
    public static int replicatorPowerMax;
    public static int replicatorPowerRecieve;
    public static int replicatorPowerUse;
    public static int replicatorProgressMax;
    public static int replicatorWorkingEntityMax;
    public static int replicatorWorkingPlayerMax;
    public static int generatorLightPowerMax;
    public static int generatorLightPowerOutput;
    public static int generatorLightMaxTicks;
    public static int generatorLightGeneration;
    public static int generatorVoidPowerMax;
    public static int generatorVoidPowerOutput;
    public static int generatorVoidMaxTicks;
    public static int generatorVoidModifier;

    public static void init(File configurationFile)
    {
        config = new Configuration(configurationFile);
        config.load();

        // General Config
        versionCheck = config.getBoolean("versionCheck", "General", true, "Whether or not to check version for recommended");

        // Ability Config
        // Atmosphere
        atmosphereEnabled = config.getBoolean("atmosphereEnabled", "Ability", true, "Whether or not the atmosphere ability should be available");
        atmosphereCost = config.getInt("atmosphereCost", "Ability", 10, 1, 100, "Cost of the atmosphere ability");
        atmosphereDuration = config.getInt("atmosphereDistance", "Ability", 15, 1, 32, "Duration of the atmosphere ability");
        // Blink
        blinkEnabled = config.getBoolean("blinkEnabled", "Ability", true, "Whether or not the blink ability should be available");
        blinkCost = config.getInt("blinkCost", "Ability", 10, 1, 100, "Cost of the blink ability");
        blinkDistance = config.getInt("blinkDistance", "Ability", 15, 1, 32, "Distance of the blink ability");
        // Fire
        fireEnabled = config.getBoolean("fireEnabled", "Ability", true, "Whether or not the fire ability should be available");
        fireCost = config.getInt("fireCost", "Ability", 4, 1, 100, "Cost of the fire ability");
        fireExtinguishCost = config.getInt("fireExtinguishCost", "Ability", 6, 1, 100, "Cost of the fire ability when extinguishing");
        fireEntityCost = config.getInt("fireEntityCost", "Ability", 8, 1, 100, "Cost of the fire ability when casting on living entities");
        fireBurnTime = config.getInt("fireBurnTime", "Ability", 5, 1, 60, "Time a living entity should burn for");
        // Fireball
        fireballEnabled = config.getBoolean("fireballEnabled", "Ability", true, "Whether or not the fireball ability should be available");
        fireballCost = config.getInt("fireballCost", "Ability", 15, 1, 100, "Cost of the fireball ability");
        // Flashstep
        flashstepEnabled = config.getBoolean("flashstepEnabled", "Ability", true, "Whether or not the flashstep ability should be available");
        flashstepCost = config.getInt("flashstepCost", "Ability", 8, 1, 100, "Cost of the flashstep ability");
        flashstepDistance = config.getInt("flashstepDistance", "Ability", 11, 1, 32, "Distance of the flashstep ability");
        // Force
        forceEnabled = config.getBoolean("forceEnabled", "Ability", true, "Whether or not the force ability should be available");
        forceCost = config.getInt("forceCost", "Ability", 6, 1, 100, "Cost of the force ability");
        // Harden
        hardenEnabled = config.getBoolean("hardenEnabled", "Ability", true, "Whether or not the harden ability should be available");
        hardenCost = config.getInt("hardenCost", "Ability", 6, 1, 100, "Cost of the harden ability");
        // Harvest
        harvestEnabled = config.getBoolean("harvestEnabled", "Ability", true, "Whether or not the harvest ability should be available");
        harvestCost = config.getInt("harvestCost", "Ability", 6, 1, 100, "Cost of the harvest ability");
        // Heal
        healEnabled = config.getBoolean("healEnabled", "Ability", true, "Whether or not the heal ability should be available");
        healCost = config.getInt("healCost", "Ability", 6, 1, 100, "Cost of the heal ability");
        // Invisibility
        invisibilityEnabled = config.getBoolean("invisibilityEnabled", "Ability", true, "Whether or not the invisibility ability should be available");
        invisibilityCost = config.getInt("invisibilityCost", "Ability", 10, 1, 100, "Cost of the invisibility ability");
        invisibilityDuration = config.getInt("invisibilityDuration", "Ability", 200, 20, 1000, "Duration of the invisibility ability");
        // Projectile
        projectileEnabled = config.getBoolean("projectileEnabled", "Abilities", true, "Whether or not the projectile ability should be available");
        projectileCost = config.getInt("projectileCost", "Abilities", 10, 1, 100, "Cost of the projectile ability");
        // Exothermic Projectile
        projectileExothermicEnabled = config.getBoolean("projectileExothermicEnabled", "Abilities", true, "Whether or not the exothermic projectile ability should be available");
        projectileExothermicCost = config.getInt("projectileExothermicCost", "Abilities", 10, 1, 100, "Cost of the exothermic projectile ability");
        // Reactive
        reactiveEnabled = config.getBoolean("reactiveEnabled", "Abilities", true, "Whether or not the reactive ability should be available");
        reactiveCost = config.getInt("reactiveCost", "Abilities", 10, 1, 100, "Cost of the reactive ability");

        // Machine Config
        // Furnace
        furnacePowerMax = config.getInt("furnacePowerMax", "Machine", 3200, 1000, 100000, "Max power a furnace can store");
        furnacePowerReceive = config.getInt("furnacePowerReceive", "Machine", 5, 1, 1000, "Max power a furnace can receive per tick");
        furnacePowerUse = config.getInt("furnacePowerUse", "Machine", 7, 1, 1000, "Power usage per tick");
        furnaceProgressMax = config.getInt("furnaceProgressMax", "Machine", 100, 100, 1000, "The maximum progress to complete smelting");
        // Separator
        separatorPowerMax = config.getInt("separatorPowerMax", "Machine", 3200, 1000, 100000, "Max power a separator can store");
        separatorPowerRecieve = config.getInt("separatorPowerRecieve", "Machine", 5, 1, 1000, "Max power a separator can receive per tick");
        separatorPowerUse = config.getInt("separatorPowerUse", "Machine", 7, 1, 1000, "Power usage per tick");
        separatorProgressMax = config.getInt("separatorProgressMax", "Machine", 100, 100, 1000, "The maximum progress to complete separating");
        // Focuser
        focuserPowerMax = config.getInt("focuserPowerMax", "Machine", 12800, 1000, 100000, "Max power a focuser can store");
        focuserPowerRecieve = config.getInt("focuserPowerRecieve", "Machine", 10, 1, 1000, "Max power a focuser can receive per tick");
        focuserPowerUse = config.getInt("focuserPowerUse", "Machine", 10, 1, 1000, "Power usage per tick");
        focuserProgressMax = config.getInt("focuserProgressMax", "Machine", 5000, 100, 100000, "The maximum progress to complete focusing");
        focuserMaxChargers = config.getInt("focuserMaxChargers", "Machine", 4, 1, 10, "The maximum amount of chargers a focuser can use");
        // Encourager
        encouragerPowerMax = config.getInt("encouragerPowerMax", "Machine", 3200, 1000, 100000, "Max power a encourager can store");
        encouragerPowerRecieve = config.getInt("encouragerPowerRecieve", "Machine", 5, 1, 1000, "Max power an encourager can receive per tick");
        encouragerPowerUse = config.getInt("encouragerPowerUse", "Machine", 2, 1, 1000, "Power usage per tick");
        encouragerProgressMax = config.getInt("encouragerProgressMax", "Machine", 100, 100, 1000, "The maximum progress to complete encouraging");
        // Analysis
        analysisPowerMax = config.getInt("analysisPowerMax", "Machine", 3200, 1000, 100000, "Max power a encourager can store");
        analysisPowerRecieve = config.getInt("analysisPowerRecieve", "Machine", 5, 1, 1000, "Max power an analysis machine can receive per tick");
        analysisPowerUse = config.getInt("analysisPowerUse", "Machine", 2, 1, 1000, "Power usage per tick");
        analysisProgressMax = config.getInt("analysisProgressMax", "Machine", 20, 10, 1000, "The maximum progress to complete analysis");
        analysisWaitingMax = config.getInt("analysisWaitingMax", "Machine", 30, 20, 100000, "Time to wait before resetting");
        analysisTicksMax = config.getInt("analysisTicksMax", "Machine", 20, 20, 100, "Ticks before progressing");
        // Crafting
        craftingPowerMax = config.getInt("craftingPowerMax", "Machine", 3200, 1000, 100000, "Max power a crafter can store");
        craftingPowerRecieve = config.getInt("craftingPowerRecieve", "Machine", 5, 1, 1000, "Max power a crafter can receive per tick");
        craftingPowerUse = config.getInt("craftingPowerUse", "Machine", 2, 1, 1000, "Power usage per tick");
        craftingProgressMax = config.getInt("craftingProgressMax", "Machine", 100, 100, 1000, "The maximum progress to complete crafting");
        // Replicator
        replicatorPowerMax = config.getInt("replicatorPowerMax", "Machine", 3200, 1000, 100000, "Max power a replicator can store");
        replicatorPowerRecieve = config.getInt("replicatorPowerRecieve", "Machine", 5, 1, 1000, "Max power a replicator can receive per tick");
        replicatorPowerUse = config.getInt("replicatorPowerUse", "Machine", 7, 1, 1000, "Power usage per tick");
        replicatorProgressMax = config.getInt("replicatorProgressMax", "Machine", 100, 100, 1000, "The maximum progress to complete replicating");
        replicatorWorkingEntityMax = config.getInt("replicatorWaitingEntity", "Machine", 100, 100, 1000, "The maximum working time for entities");
        replicatorWorkingPlayerMax = config.getInt("replicatorWaitingPlayer", "Machine", 60, 60, 1000, "The maximum working time for player");
        // Generators
        // Light
        generatorLightPowerMax = config.getInt("generatorLightPowerMax", "Machine", 3200, 1000, 100000, "Internal storage for the light generator");
        generatorLightPowerOutput = config.getInt("generatorLightPowerMax", "Machine", 5, 1, 100, "Maximum output for the light generator");
        generatorLightMaxTicks = config.getInt("generatorLightMaxTicks", "Machine", 40, 1, 100, "Amount of ticks before energy is generated");
        generatorLightGeneration = config.getInt("generatorLightGeneration", "Machine", 2, 1, 100, "Amount of energy generated");
        // Void
        generatorVoidPowerMax = config.getInt("generatorVoidPowerMax", "Machine", 3200, 1000, 100000, "Internal storage for the void generator");
        generatorVoidPowerOutput = config.getInt("generatorVoidPowerOutput", "Machine", 5, 1, 100, "Maximum output for the void generator");
        generatorVoidMaxTicks = config.getInt("generatorVoidMaxTicks", "Machine", 40, 1, 100, "Amount of ticks before energy is generated");
        generatorVoidModifier = config.getInt("generatorVoidModifier", "Machine", 5, 1, 10, "Modifier for energy generation, higher the number, the lower the energy");

        config.save();
    }
}
