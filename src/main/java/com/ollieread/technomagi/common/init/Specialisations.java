package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.api.ISpecialisation;
import com.ollieread.technomagi.api.Specialisation;
import com.ollieread.technomagi.api.TMRegistry;

public class Specialisations
{

    public static ISpecialisation specScholar;
    public static ISpecialisation specMedic;
    public static ISpecialisation specWarrior;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering specialisations");

        specScholar = new Specialisation("scholar");
        specMedic = new Specialisation("medic");
        specWarrior = new Specialisation("warrior");

        TMRegistry.registerSpecialisation(specScholar);
        TMRegistry.registerSpecialisation(specMedic);
        TMRegistry.registerSpecialisation(specWarrior);
    }

}
