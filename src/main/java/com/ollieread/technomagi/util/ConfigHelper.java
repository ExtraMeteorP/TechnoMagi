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

        abilityState.put("exothermic", config.getBoolean("exothermic", "Ability State", true, "Enable/Disable exothermic crafting ability"));
        abilityState.put("endothermic", config.getBoolean("endothermic", "Ability State", true, "Enable/Disable endothermic crafting ability"));

        config.save();
    }
}
