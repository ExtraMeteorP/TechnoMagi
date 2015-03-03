package com.ollieread.technomagi.common.init;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.ability.IAbilityCast;
import com.ollieread.technomagi.common.ability.cast.AbilityCastNanitesForFood;
import com.ollieread.technomagi.common.ability.cast.AbilityCastRegenerate;
import com.ollieread.technomagi.common.ability.cast.AbilityCastScan;
import com.ollieread.technomagi.util.ResourceHelper;

public class Abilities
{

    public static IAbilityCast scan;
    public static IAbilityCast regenerate;
    public static IAbilityCast naniteFood;

    public static void init()
    {
        scan = new AbilityCastScan("scan", ResourceHelper.texture("abilities/scan.png"));
        regenerate = new AbilityCastRegenerate("regenerate", ResourceHelper.texture("abilities/regenerate.png"));
        naniteFood = new AbilityCastNanitesForFood("nanites_for_food", ResourceHelper.texture("abilities/food.png"));

        TechnomagiApi.ability().addCastableAbility(scan);
        TechnomagiApi.ability().addCastableAbility(regenerate);
        TechnomagiApi.ability().addCastableAbility(naniteFood);
    }

}
