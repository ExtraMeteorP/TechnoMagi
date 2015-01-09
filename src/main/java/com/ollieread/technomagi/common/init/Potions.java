package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.potion.PotionTM;

public class Potions
{

    public static PotionTM potionHardness;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering potions");

        // potionHardness = new PotionTM(30, "potionHardness", false, 0);
    }
}
