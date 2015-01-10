package com.ollieread.technomagi.common.init;

import java.io.File;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.util.ItemHelper;

public class Config
{

    public static Configuration config;

    public static String CATEGORY_GENERAL = "General";
    public static String CATEGORY_ENTITY = "Entity";
    public static String CATEGORY_ABILITY = "Ability";
    public static String CATEGORY_MACHINE = "Machine";
    public static String CATEGORY_ENVIRONMENT = "Environment";

    // General
    public static boolean debugMode;
    public static boolean versionCheck;
    public static boolean creativeKnowledge;
    public static boolean creativeKnowledgeAll;
    // Entity
    public static int brainDropChance;
    public static int robotCow;
    public static int robotZombie;
    public static int robotCreeper;
    public static int robotEnderman;
    public static int robotVillager;
    // Abilities
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
    public static int healDuration;
    public static boolean invisibilityEnabled;
    public static int invisibilityCost;
    public static int invisibilityDuration;
    public static boolean projectileEnabled;
    public static int projectileCost;
    public static boolean projectileExothermicEnabled;
    public static int projectileExothermicCost;
    public static boolean reactiveEnabled;
    public static int reactiveCost;
    public static boolean shieldEnabled;
    public static int shieldCost;
    public static int shieldDuration;
    public static boolean drainEnabled;
    public static boolean overdriveEnabled;
    public static int overdriveCost;
    public static int overdriveDuration;
    public static boolean flightEnabled;
    public static int flightCost;
    public static int flightDuration;
    // Machines
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
    // Environment
    public static int voidBreachMaxTicks;
    public static int voidBreachSpreadChance;
    public static int voidBreachStageChance;
    public static int voidBreachStageSpread;
    public static ItemStack[] voidBreachImmune;
    public static boolean perceptionEnabled;
    public static int[] perceptionDimensions;

    public static void init(File configurationFile)
    {
        config = new Configuration(configurationFile);
        config.load();

        // General Config
        debugMode = config.getBoolean("debugMode", CATEGORY_GENERAL, false, "Whether or not enable debug messages");
        versionCheck = config.getBoolean("versionCheck", CATEGORY_GENERAL, true, "Whether or not to check version for recommended");
        creativeKnowledge = config.getBoolean("creativeKnowledge", CATEGORY_GENERAL, false, "Whether or not to allow giving knowledge from creative");
        creativeKnowledgeAll = config.getBoolean("creativeKnowledgeAll", CATEGORY_GENERAL, false, "Whether or not to allow giving all knowledge from creative");

        // Entities
        brainDropChance = config.getInt("brainDropChance", CATEGORY_ENTITY, 100, 20, 500, "The chance of dropping a brain when killing a mob");
        robotCow = config.getInt("robotCow", CATEGORY_ENTITY, 1, 1, 100, "Robot cow ID");
        robotZombie = config.getInt("robotZombie", CATEGORY_ENTITY, 1, 1, 100, "Robot zombie ID");
        robotCreeper = config.getInt("robotCreeper", CATEGORY_ENTITY, 1, 1, 100, "Robot creeper ID");
        robotEnderman = config.getInt("robotEnderman", CATEGORY_ENTITY, 1, 1, 100, "Robot enderman ID");
        robotVillager = config.getInt("robotVillager", CATEGORY_ENTITY, 1, 1, 100, "Robot villager ID");

        // Ability Config
        // Atmosphere
        atmosphereEnabled = config.getBoolean("atmosphereEnabled", CATEGORY_ABILITY, true, "Whether or not the atmosphere ability should be available");
        atmosphereCost = config.getInt("atmosphereCost", CATEGORY_ABILITY, 10, 1, 100, "Cost of the atmosphere ability");
        atmosphereDuration = config.getInt("atmosphereDuration", CATEGORY_ABILITY, 200, 1, 36000, "Duration of the atmosphere ability");
        // Blink
        blinkEnabled = config.getBoolean("blinkEnabled", CATEGORY_ABILITY, true, "Whether or not the blink ability should be available");
        blinkCost = config.getInt("blinkCost", CATEGORY_ABILITY, 10, 1, 100, "Cost of the blink ability");
        blinkDistance = config.getInt("blinkDistance", CATEGORY_ABILITY, 15, 1, 32, "Distance of the blink ability");
        // Fire
        fireEnabled = config.getBoolean("fireEnabled", CATEGORY_ABILITY, true, "Whether or not the fire ability should be available");
        fireCost = config.getInt("fireCost", CATEGORY_ABILITY, 4, 1, 100, "Cost of the fire ability");
        fireExtinguishCost = config.getInt("fireExtinguishCost", CATEGORY_ABILITY, 6, 1, 100, "Cost of the fire ability when extinguishing");
        fireEntityCost = config.getInt("fireEntityCost", CATEGORY_ABILITY, 8, 1, 100, "Cost of the fire ability when casting on living entities");
        fireBurnTime = config.getInt("fireBurnTime", CATEGORY_ABILITY, 5, 1, 60, "Time a living entity should burn for");
        // Fireball
        fireballEnabled = config.getBoolean("fireballEnabled", CATEGORY_ABILITY, true, "Whether or not the fireball ability should be available");
        fireballCost = config.getInt("fireballCost", CATEGORY_ABILITY, 15, 1, 100, "Cost of the fireball ability");
        // Flashstep
        flashstepEnabled = config.getBoolean("flashstepEnabled", CATEGORY_ABILITY, true, "Whether or not the flashstep ability should be available");
        flashstepCost = config.getInt("flashstepCost", CATEGORY_ABILITY, 8, 1, 100, "Cost of the flashstep ability");
        flashstepDistance = config.getInt("flashstepDistance", CATEGORY_ABILITY, 11, 1, 32, "Distance of the flashstep ability");
        // Force
        forceEnabled = config.getBoolean("forceEnabled", CATEGORY_ABILITY, true, "Whether or not the force ability should be available");
        forceCost = config.getInt("forceCost", CATEGORY_ABILITY, 6, 1, 100, "Cost of the force ability");
        // Harden
        hardenEnabled = config.getBoolean("hardenEnabled", CATEGORY_ABILITY, true, "Whether or not the harden ability should be available");
        hardenCost = config.getInt("hardenCost", CATEGORY_ABILITY, 6, 1, 100, "Cost of the harden ability");
        // Harvest
        harvestEnabled = config.getBoolean("harvestEnabled", CATEGORY_ABILITY, true, "Whether or not the harvest ability should be available");
        harvestCost = config.getInt("harvestCost", CATEGORY_ABILITY, 6, 1, 100, "Cost of the harvest ability");
        // Heal
        healEnabled = config.getBoolean("healEnabled", CATEGORY_ABILITY, true, "Whether or not the heal ability should be available");
        healCost = config.getInt("healCost", CATEGORY_ABILITY, 6, 1, 100, "Cost of the heal ability");
        healDuration = config.getInt("healDuration", CATEGORY_ABILITY, 100, 20, 36000, "Duration of the heal ability");
        // Invisibility
        invisibilityEnabled = config.getBoolean("invisibilityEnabled", CATEGORY_ABILITY, true, "Whether or not the invisibility ability should be available");
        invisibilityCost = config.getInt("invisibilityCost", CATEGORY_ABILITY, 10, 1, 100, "Cost of the invisibility ability");
        invisibilityDuration = config.getInt("invisibilityDuration", CATEGORY_ABILITY, 200, 20, 36000, "Duration of the invisibility ability");
        // Projectile
        projectileEnabled = config.getBoolean("projectileEnabled", CATEGORY_ABILITY, true, "Whether or not the projectile ability should be available");
        projectileCost = config.getInt("projectileCost", CATEGORY_ABILITY, 10, 1, 100, "Cost of the projectile ability");
        // Exothermic Projectile
        projectileExothermicEnabled = config.getBoolean("projectileExothermicEnabled", CATEGORY_ABILITY, true, "Whether or not the exothermic projectile ability should be available");
        projectileExothermicCost = config.getInt("projectileExothermicCost", CATEGORY_ABILITY, 10, 1, 100, "Cost of the exothermic projectile ability");
        // Reactive
        reactiveEnabled = config.getBoolean("reactiveEnabled", CATEGORY_ABILITY, true, "Whether or not the reactive ability should be available");
        reactiveCost = config.getInt("reactiveCost", CATEGORY_ABILITY, 10, 1, 100, "Cost of the reactive ability");
        // Shield
        shieldEnabled = config.getBoolean("shieldEnabled", CATEGORY_ABILITY, true, "Whether or not the shield ability should be available");
        shieldCost = config.getInt("shieldCost", CATEGORY_ABILITY, 20, 1, 100, "Cost of the shield ability");
        shieldDuration = config.getInt("shieldDuration", CATEGORY_ABILITY, 200, 20, 36000, "Duration of the shield ability");
        // Drain
        drainEnabled = config.getBoolean("drainEnabled", CATEGORY_ABILITY, true, "Whether or not the drain ability should be available");
        // Overdrive
        overdriveEnabled = config.getBoolean("overdriveEnabled", CATEGORY_ABILITY, true, "Whether or not the overdrive ability should be available");
        overdriveCost = config.getInt("overdriveCost", CATEGORY_ABILITY, 20, 1, 100, "Cost of the overdrive ability");
        overdriveDuration = config.getInt("overdriveDuration", CATEGORY_ABILITY, 1000, 20, 36000, "Duration of the overdrive ability");
        // Flight
        flightEnabled = config.getBoolean("flightEnabled", CATEGORY_ABILITY, true, "Whether or not the flight ability is enabled");
        flightCost = config.getInt("flightCost", CATEGORY_ABILITY, 10, 1, 100, "The cost of the flight ability");
        flightDuration = config.getInt("flightDuration", CATEGORY_ABILITY, 1200, 100, 12000, "The duration of the flight");

        // Machine Config
        // Furnace
        furnacePowerMax = config.getInt("furnacePowerMax", CATEGORY_MACHINE, 3200, 1000, 100000, "Max power a furnace can store");
        furnacePowerReceive = config.getInt("furnacePowerReceive", CATEGORY_MACHINE, 5, 1, 1000, "Max power a furnace can receive per tick");
        furnacePowerUse = config.getInt("furnacePowerUse", CATEGORY_MACHINE, 7, 1, 1000, "Power usage per tick");
        furnaceProgressMax = config.getInt("furnaceProgressMax", CATEGORY_MACHINE, 100, 100, 1000, "The maximum progress to complete smelting");
        // Separator
        separatorPowerMax = config.getInt("separatorPowerMax", CATEGORY_MACHINE, 3200, 1000, 100000, "Max power a separator can store");
        separatorPowerRecieve = config.getInt("separatorPowerRecieve", CATEGORY_MACHINE, 5, 1, 1000, "Max power a separator can receive per tick");
        separatorPowerUse = config.getInt("separatorPowerUse", CATEGORY_MACHINE, 7, 1, 1000, "Power usage per tick");
        separatorProgressMax = config.getInt("separatorProgressMax", CATEGORY_MACHINE, 100, 100, 1000, "The maximum progress to complete separating");
        // Focuser
        focuserPowerMax = config.getInt("focuserPowerMax", CATEGORY_MACHINE, 12800, 1000, 100000, "Max power a focuser can store");
        focuserPowerRecieve = config.getInt("focuserPowerRecieve", CATEGORY_MACHINE, 10, 1, 1000, "Max power a focuser can receive per tick");
        focuserPowerUse = config.getInt("focuserPowerUse", CATEGORY_MACHINE, 10, 1, 1000, "Power usage per tick");
        focuserProgressMax = config.getInt("focuserProgressMax", CATEGORY_MACHINE, 5000, 100, 100000, "The maximum progress to complete focusing");
        focuserMaxChargers = config.getInt("focuserMaxChargers", CATEGORY_MACHINE, 4, 1, 10, "The maximum amount of chargers a focuser can use");
        // Encourager
        encouragerPowerMax = config.getInt("encouragerPowerMax", CATEGORY_MACHINE, 3200, 1000, 100000, "Max power a encourager can store");
        encouragerPowerRecieve = config.getInt("encouragerPowerRecieve", CATEGORY_MACHINE, 5, 1, 1000, "Max power an encourager can receive per tick");
        encouragerPowerUse = config.getInt("encouragerPowerUse", CATEGORY_MACHINE, 2, 1, 1000, "Power usage per tick");
        encouragerProgressMax = config.getInt("encouragerProgressMax", CATEGORY_MACHINE, 100, 100, 1000, "The maximum progress to complete encouraging");
        // Analysis
        analysisPowerMax = config.getInt("analysisPowerMax", CATEGORY_MACHINE, 3200, 1000, 100000, "Max power a encourager can store");
        analysisPowerRecieve = config.getInt("analysisPowerRecieve", CATEGORY_MACHINE, 5, 1, 1000, "Max power an analysis machine can receive per tick");
        analysisPowerUse = config.getInt("analysisPowerUse", CATEGORY_MACHINE, 2, 1, 1000, "Power usage per tick");
        analysisProgressMax = config.getInt("analysisProgressMax", CATEGORY_MACHINE, 200, 20, 1000, "The maximum progress to complete analysis");
        analysisWaitingMax = config.getInt("analysisWaitingMax", CATEGORY_MACHINE, 30, 20, 100000, "Time to wait before resetting");
        // Crafting
        craftingPowerMax = config.getInt("craftingPowerMax", CATEGORY_MACHINE, 3200, 1000, 100000, "Max power a crafter can store");
        craftingPowerRecieve = config.getInt("craftingPowerRecieve", CATEGORY_MACHINE, 5, 1, 1000, "Max power a crafter can receive per tick");
        craftingPowerUse = config.getInt("craftingPowerUse", CATEGORY_MACHINE, 2, 1, 1000, "Power usage per tick");
        craftingProgressMax = config.getInt("craftingProgressMax", CATEGORY_MACHINE, 100, 100, 1000, "The maximum progress to complete crafting");
        // Replicator
        replicatorPowerMax = config.getInt("replicatorPowerMax", CATEGORY_MACHINE, 3200, 1000, 100000, "Max power a replicator can store");
        replicatorPowerRecieve = config.getInt("replicatorPowerRecieve", CATEGORY_MACHINE, 5, 1, 1000, "Max power a replicator can receive per tick");
        replicatorPowerUse = config.getInt("replicatorPowerUse", CATEGORY_MACHINE, 7, 1, 1000, "Power usage per tick");
        replicatorProgressMax = config.getInt("replicatorProgressMax", CATEGORY_MACHINE, 100, 100, 1000, "The maximum progress to complete replicating");
        replicatorWorkingEntityMax = config.getInt("replicatorWaitingEntity", CATEGORY_MACHINE, 100, 100, 1000, "The maximum working time for entities");
        replicatorWorkingPlayerMax = config.getInt("replicatorWaitingPlayer", CATEGORY_MACHINE, 60, 60, 1000, "The maximum working time for player");
        // Generators
        // Light
        generatorLightPowerMax = config.getInt("generatorLightPowerMax", CATEGORY_MACHINE, 3200, 1000, 100000, "Internal storage for the light generator");
        generatorLightPowerOutput = config.getInt("generatorLightPowerMax", CATEGORY_MACHINE, 5, 1, 100, "Maximum output for the light generator");
        generatorLightMaxTicks = config.getInt("generatorLightMaxTicks", CATEGORY_MACHINE, 40, 1, 100, "Amount of ticks before energy is generated");
        generatorLightGeneration = config.getInt("generatorLightGeneration", CATEGORY_MACHINE, 2, 1, 100, "Amount of energy generated");
        // Void
        generatorVoidPowerMax = config.getInt("generatorVoidPowerMax", CATEGORY_MACHINE, 3200, 1000, 100000, "Internal storage for the void generator");
        generatorVoidPowerOutput = config.getInt("generatorVoidPowerOutput", CATEGORY_MACHINE, 5, 1, 100, "Maximum output for the void generator");
        generatorVoidMaxTicks = config.getInt("generatorVoidMaxTicks", CATEGORY_MACHINE, 40, 1, 100, "Amount of ticks before energy is generated");
        generatorVoidModifier = config.getInt("generatorVoidModifier", CATEGORY_MACHINE, 5, 1, 10, "Modifier for energy generation, higher the number, the lower the energy");

        // Environment
        // Void breach
        voidBreachMaxTicks = config.getInt("voidBreachMaxTicks", CATEGORY_ENVIRONMENT, 6000, 1200, 100000, "Max ticks for a chance for the void breach to progress");
        voidBreachSpreadChance = config.getInt("voidBreachSpreadChance", CATEGORY_ENVIRONMENT, 100, 100, 100000, "Chance for the void breach to spread");
        voidBreachStageChance = config.getInt("voidBreachStageChance", CATEGORY_ENVIRONMENT, 100, 100, 100000, "Chance for the void breach to stage up");
        voidBreachStageSpread = config.getInt("voidBreachStageSpread", CATEGORY_ENVIRONMENT, 9, 0, 9, "Minimum stage the void breach must be to spread");
        // Perception filter
        perceptionEnabled = config.getBoolean("perceptionEnabled", CATEGORY_ENVIRONMENT, true, "Whether or not to allow perception filtering");
        perceptionDimensions = getIntList("perceptionDimensions", CATEGORY_ENVIRONMENT, new int[] { 0 }, "Dimensions the perception filter works with");

        config.save();
    }

    public static void loadImmuneBlocks()
    {
        config.load();

        String[] nonReplaceable = config.getStringList("voidBreachImmune", CATEGORY_ENVIRONMENT, new String[] { Reference.MODID + ":resourceBlock:1", Reference.MODID + ":hardlight", "minecraft:bedrock" }, "Blocks that are immune to the void breach spread");

        voidBreachImmune = new ItemStack[nonReplaceable.length];

        for (int i = 0; i < voidBreachImmune.length; i++) {
            String name = nonReplaceable[i];
            String[] blockParts = name.split(":");

            if (blockParts.length == 2) {
                voidBreachImmune[i] = ItemHelper.block(blockParts[0], blockParts[1]);
            } else if (blockParts.length == 3) {
                voidBreachImmune[i] = ItemHelper.block(blockParts[0], blockParts[1], Integer.parseInt(blockParts[2]));
            }
        }

        config.save();
    }

    public static int[] getIntList(String name, String category, int[] defaultValue, String comment)
    {
        Property prop = config.get(category, name, defaultValue);
        prop.setLanguageKey(name);
        prop.setValidValues(null);
        prop.comment = comment + " [default: " + prop.getDefault() + "]";
        return prop.getIntList();
    }
}
