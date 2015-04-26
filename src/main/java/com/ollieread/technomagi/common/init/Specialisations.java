package com.ollieread.technomagi.common.init;

import net.minecraft.entity.SharedMonsterAttributes;

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
        cleric = new Specialisation("cleric", ResourceHelper.texture("specialisations/cleric.png"));
        engineer = new Specialisation("engineer", ResourceHelper.texture("specialisations/engineer.png"));
        guardian = new Specialisation("guardian", ResourceHelper.texture("specialisations/guardian.png"));
        scholar = new Specialisation("scholar", ResourceHelper.texture("specialisations/scholar.png"));
        shadow = new Specialisation("shadow", ResourceHelper.texture("specialisations/shadow.png"));

        cleric.addAttribute(SharedMonsterAttributes.maxHealth, "e3ce66c0-122c-44a9-8936-47ae591cd993", 2.0D, 0);
        // engineer.addAttribute(attribute, name, amount, operation);
        guardian.addAttribute(SharedMonsterAttributes.knockbackResistance, "31d80b6f-bbde-4bab-9673-96217ff47e1d", 1.0D, 0);
        // scholar.addAttribute(attribute, name, amount, operation);
        shadow.addAttribute(SharedMonsterAttributes.movementSpeed, "ef7ded46-2636-4276-b10f-248161793003", 0.20000000298023224D, 2);

        TechnomagiApi.specialisation().addSpecialisation(cleric);
        TechnomagiApi.specialisation().addSpecialisation(engineer);
        TechnomagiApi.specialisation().addSpecialisation(guardian);
        TechnomagiApi.specialisation().addSpecialisation(scholar);
        TechnomagiApi.specialisation().addSpecialisation(shadow);
    }
}
