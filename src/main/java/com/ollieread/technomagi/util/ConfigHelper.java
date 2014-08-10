package com.ollieread.technomagi.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;

public class ConfigHelper
{

    public static Map<String, Boolean> abilityState = new HashMap<String, Boolean>();

    public static int robotCowID;
    public static int robotCreeperID;
    public static int robotZombieID;

    public static void loadConfiguration(File file)
    {
        Configuration config = new Configuration(file);
        config.load();

        abilityState.put("fire", config.getBoolean("fire", "Ability State", true, "Enable/Disable pyromancy ability"));
        abilityState.put("fireball", config.getBoolean("fireball", "Ability State", true, "Enable/Disable fireball ability"));
        abilityState.put("harden", config.getBoolean("harden", "Ability State", true, "Enable/Disable harden ability"));
        abilityState.put("invisibility", config.getBoolean("invisibility", "Ability State", true, "Enable/Disable invisibility ability"));
        abilityState.put("flashstep", config.getBoolean("flashstep", "Ability State", true, "Enable/Disable flashstep ability"));
        abilityState.put("blink", config.getBoolean("blink", "Ability State", true, "Enable/Disable blink ability"));
        abilityState.put("knockback", config.getBoolean("knockback", "Ability State", true, "Enable/Disable knockback entity ability"));
        abilityState.put("expel", config.getBoolean("expel", "Ability State", true, "Enable/Disable expel entities ability"));
        abilityState.put("scholarResearch", config.getBoolean("scholarResearch", "Ability State", true, "Enable/Disable scholar research boost ability"));
        abilityState.put("naniteRegen", config.getBoolean("naniteRegen", "Ability State", true, "Enable/Disable nanite regeneration ability"));
        abilityState.put("negateFall", config.getBoolean("negateFall", "Ability State", true, "Enable/Disable fall negation ability"));

        robotCowID = config.get("Robot Entities", "robotCow", EntityHelper.getUniqueEntityId()).getInt();
        robotCreeperID = config.get("Robot Entities", "robotCreeper", EntityHelper.getUniqueEntityId()).getInt();
        robotZombieID = config.get("Robot Entities", "robotZombie", EntityHelper.getUniqueEntityId()).getInt();

        config.save();
    }
}
