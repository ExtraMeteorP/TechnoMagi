package com.ollieread.technomagi.common.init;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config
{

    public static Configuration config;

    public static boolean versionCheck;

    public static boolean reactiveEnabled;
    public static int reactiveCost;
    public static boolean projectileEnabled;
    public static int projectileCost;
    public static boolean projectileExothermicEnabled;
    public static int projectileExothermicCost;

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

        reactiveEnabled = config.getBoolean("reactive", "Ability States", true, null);
        reactiveCost = config.getInt("reactive", "Ability Cost", 10, 1, 100, null);
        projectileEnabled = config.getBoolean("projectile", "Ability States", true, null);
        projectileCost = config.getInt("projectile", "Ability Cost", 10, 1, 100, null);
        projectileExothermicEnabled = config.getBoolean("projectileExothermic", "Ability States", true, null);
        projectileExothermicCost = config.getInt("projectileExothermic", "Ability Cost", 10, 1, 100, null);

        config.save();
    }
}
