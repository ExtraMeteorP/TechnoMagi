package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.ability.ActiveAbilityFire;
import com.ollieread.technomagi.ability.ActiveAbilityFlashstep;
import com.ollieread.technomagi.ability.ActiveAbilityHarden;
import com.ollieread.technomagi.ability.ActiveAbilityInvisibility;
import com.ollieread.technomagi.ability.PassiveAbilityNanites;
import com.ollieread.technomagi.ability.PassiveAbilityNegateFall;
import com.ollieread.technomagi.ability.PassiveAbilityScholarResearch;
import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.ability.IAbilityActive;
import com.ollieread.technomagi.api.ability.IAbilityPassive;

public class Abilities
{

    public static IAbilityActive abilityActiveFire;
    public static IAbilityActive abilityActiveHarden;
    public static IAbilityActive abilityActiveInvisibility;
    public static IAbilityActive abilityActiveFlashstep;

    public static IAbilityPassive abilityPassiveScholarResearch;
    public static IAbilityPassive abilityPassiveNanites;
    public static IAbilityPassive abilityPassiveNegateFall;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering abilities");

        abilityActiveFire = new ActiveAbilityFire("fire");
        abilityActiveHarden = new ActiveAbilityHarden("harden");
        abilityActiveInvisibility = new ActiveAbilityInvisibility("invisibility");
        abilityActiveFlashstep = new ActiveAbilityFlashstep("flashstep");

        TMRegistry.registerActiveAbility(abilityActiveFire);
        TMRegistry.registerActiveAbility(abilityActiveHarden);
        TMRegistry.registerActiveAbility(abilityActiveInvisibility);
        TMRegistry.registerActiveAbility(abilityActiveFlashstep);

        abilityPassiveScholarResearch = new PassiveAbilityScholarResearch("scholarResearch");
        abilityPassiveNanites = new PassiveAbilityNanites("naniteRegen");
        abilityPassiveNegateFall = new PassiveAbilityNegateFall("negateFall");

        TMRegistry.registerPassiveAbility(abilityPassiveScholarResearch);
        TMRegistry.registerPassiveAbility(abilityPassiveNanites);
        TMRegistry.registerPassiveAbility(abilityPassiveNegateFall);
    }

}
