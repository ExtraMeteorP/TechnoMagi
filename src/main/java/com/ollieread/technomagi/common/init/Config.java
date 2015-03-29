package com.ollieread.technomagi.common.init;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config
{

    public static Configuration config;

    // Categories
    public static String CATEGORY_GENERAL = "General";
    public static String CATEGORY_TELEPORTER = "Teleporter";
    public static String CATEGORY_ENTITY = "Entity";
    public static String CATEGORY_ABILITY = "Ability";
    public static String CATEGORY_MACHINE = "Machine";
    public static String CATEGORY_ENVIRONMENT = "Environment";

    // General
    public static boolean debugMode;
    public static boolean versionCheck;
    public static boolean creativeKnowledge;
    public static boolean creativeKnowledgeAll;
    public static boolean multiplayerMode;

    // Teleporters
    public static int elevatorLength;
    public static int elevatorBlockJump;

    // Entity
    public static int robotCow;
    public static int robotZombie;
    public static int robotCreeper;
    public static int robotEnderman;
    public static int robotVillager;
    public static int zombieCow;
    public static int zombieSheep;

    public static void init(File configurationFile)
    {
        config = new Configuration(configurationFile);
        config.load();

        // General Config
        debugMode = config.getBoolean("debugMode", CATEGORY_GENERAL, false, "Whether or not enable debug messages");
        versionCheck = config.getBoolean("versionCheck", CATEGORY_GENERAL, true, "Whether or not to check version for recommended");
        creativeKnowledge = config.getBoolean("creativeKnowledge", CATEGORY_GENERAL, false, "Whether or not to allow giving knowledge from creative");
        creativeKnowledgeAll = config.getBoolean("creativeKnowledgeAll", CATEGORY_GENERAL, false, "Whether or not to allow giving all knowledge from creative");
        multiplayerMode = config.getBoolean("multiplayerMode", CATEGORY_GENERAL, true, "Whether or not to apply class bias for multiplayer, best to leave on");

        // Teleporter Config
        elevatorLength = config.getInt("elevatorLength", CATEGORY_TELEPORTER, 100, 3, 256, "The maximum length that the elevators can be from each other");
        elevatorBlockJump = config.getInt("elevatorBlockJump", CATEGORY_TELEPORTER, 20, 0, 256, "The amount of solid blocks the elevator can jump");

        // Entity Config
        robotCow = config.getInt("robotCow", CATEGORY_ENTITY, 1, 1, 100, "Robot cow ID");
        robotZombie = config.getInt("robotZombie", CATEGORY_ENTITY, 2, 1, 100, "Robot zombie ID");
        robotCreeper = config.getInt("robotCreeper", CATEGORY_ENTITY, 3, 1, 100, "Robot creeper ID");
        robotEnderman = config.getInt("robotEnderman", CATEGORY_ENTITY, 4, 1, 100, "Robot enderman ID");
        robotVillager = config.getInt("robotVillager", CATEGORY_ENTITY, 5, 1, 100, "Robot villager ID");
        zombieCow = config.getInt("zombieCow", CATEGORY_ENTITY, 6, 1, 100, "Zombie cow ID");
        zombieSheep = config.getInt("zombieSheep", CATEGORY_ENTITY, 7, 1, 100, "Zombie sheep ID");

        config.save();
    }
}
