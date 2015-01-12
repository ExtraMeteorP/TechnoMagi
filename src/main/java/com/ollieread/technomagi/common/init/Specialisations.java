package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.ISpecialisation;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.specialisation.SpecialisationCleric;
import com.ollieread.technomagi.specialisation.SpecialisationEngineer;
import com.ollieread.technomagi.specialisation.SpecialisationGuardian;
import com.ollieread.technomagi.specialisation.SpecialisationScholar;
import com.ollieread.technomagi.specialisation.SpecialisationShadow;

public class Specialisations
{

    public static ISpecialisation scholar;
    public static ISpecialisation cleric;
    public static ISpecialisation guardian;
    public static ISpecialisation engineer;
    public static ISpecialisation shadow;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering specialisations");

        scholar = new SpecialisationScholar();
        cleric = new SpecialisationCleric();
        guardian = new SpecialisationGuardian();
        engineer = new SpecialisationEngineer();
        shadow = new SpecialisationShadow();
    }
}
