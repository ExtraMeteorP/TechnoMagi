package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.ability.IAbilityActive;
import com.ollieread.ennds.ability.IAbilityPassive;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.ability.active.ActiveAbilityEndothermic;
import com.ollieread.technomagi.ability.active.ActiveAbilityExothermic;
import com.ollieread.technomagi.ability.passive.PassiveAbilityNanites;
import com.ollieread.technomagi.util.ConfigHelper;

public class Abilities
{

    public static IAbilityActive abilityActiveExothermic;
    public static IAbilityActive abilityActiveEndothermic;
    public static IAbilityActive abilityActiveFire;
    public static IAbilityActive abilityActiveFireball;
    public static IAbilityActive abilityActiveHarden;
    public static IAbilityActive abilityActiveInvisibility;
    public static IAbilityActive abilityActiveFlashstep;
    public static IAbilityActive abilityActiveBlink;
    public static IAbilityActive abilityActiveForceTarget;
    public static IAbilityActive abilityActiveForceArea;
    public static IAbilityActive abilityActiveHarvestI;
    public static IAbilityActive abilityActiveHarvestII;
    public static IAbilityActive abilityActiveHarvestIII;
    public static IAbilityActive abilityActiveAtmosphereI;

    public static IAbilityPassive abilityPassiveScholarResearch;
    public static IAbilityPassive abilityPassiveNanites;
    public static IAbilityPassive abilityPassiveNegateFall;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering abilities");

        if (ConfigHelper.abilityState.get("exothermic")) {
            abilityActiveExothermic = new ActiveAbilityExothermic("exothermic");
        }
        if (ConfigHelper.abilityState.get("endothermic")) {
            abilityActiveEndothermic = new ActiveAbilityEndothermic("endothermic");
        }

        if (ConfigHelper.abilityState.get("nanites")) {
            abilityPassiveNanites = new PassiveAbilityNanites("nanites");
        }

    }
}
