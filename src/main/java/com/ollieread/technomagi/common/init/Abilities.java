package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.ability.IAbilityActive;
import com.ollieread.ennds.ability.IAbilityPassive;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.ability.active.ActiveAbilityFire;
import com.ollieread.technomagi.ability.active.ActiveAbilityFlashstep;
import com.ollieread.technomagi.ability.active.ActiveAbilityHarden;
import com.ollieread.technomagi.ability.active.ActiveAbilityInvisibility;
import com.ollieread.technomagi.ability.passive.PassiveAbilityNanites;
import com.ollieread.technomagi.ability.passive.PassiveAbilityNegateFall;
import com.ollieread.technomagi.ability.passive.PassiveAbilityScholarResearch;

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

        abilityPassiveScholarResearch = new PassiveAbilityScholarResearch("scholarResearch");
        abilityPassiveNanites = new PassiveAbilityNanites("naniteRegen");
        abilityPassiveNegateFall = new PassiveAbilityNegateFall("negateFall");
    }

}
