package com.ollieread.technomagi.common.init;

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
        specScholar = new Specialisation("specScholar");
        specMedic = new Specialisation("specMedic");
        specWarrior = new Specialisation("specWarrior");

        TMRegistry.registerSpecialisation(specScholar);
        TMRegistry.registerSpecialisation(specMedic);
        TMRegistry.registerSpecialisation(specWarrior);
    }

}
