package com.ollieread.technomagi.common.init;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.specialisation.Specialisation;
import com.ollieread.technomagi.util.ResourceHelper;

public class Specialisations
{

    public static Specialisation cleric;
    public static Specialisation engineer;
    public static Specialisation guardian;
    public static Specialisation scholar;
    public static Specialisation shadow;

    public static void init()
    {
        cleric = TechnomagiApi.specialisation().addSpecialisation(new Specialisation("cleric", ResourceHelper.texture("specialisations/cleric.png")));
        engineer = TechnomagiApi.specialisation().addSpecialisation(new Specialisation("engineer", ResourceHelper.texture("specialisations/engineer.png")));
        guardian = TechnomagiApi.specialisation().addSpecialisation(new Specialisation("guardian", ResourceHelper.texture("specialisations/guardian.png")));
        scholar = TechnomagiApi.specialisation().addSpecialisation(new Specialisation("scholar", ResourceHelper.texture("specialisations/scholar.png")));
        shadow = TechnomagiApi.specialisation().addSpecialisation(new Specialisation("shadow", ResourceHelper.texture("specialisations/shadow.png")));
    }

}
