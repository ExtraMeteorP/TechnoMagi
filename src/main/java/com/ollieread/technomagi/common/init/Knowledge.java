package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;

public class Knowledge
{

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering knowledge");
    }

}
