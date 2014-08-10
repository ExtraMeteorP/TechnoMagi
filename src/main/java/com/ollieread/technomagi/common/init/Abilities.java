package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.ability.IAbilityActive;
import com.ollieread.ennds.ability.IAbilityPassive;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.ability.active.ActiveAbilityBlink;
import com.ollieread.technomagi.ability.active.ActiveAbilityFire;
import com.ollieread.technomagi.ability.active.ActiveAbilityFireball;
import com.ollieread.technomagi.ability.active.ActiveAbilityFlashstep;
import com.ollieread.technomagi.ability.active.ActiveAbilityForceArea;
import com.ollieread.technomagi.ability.active.ActiveAbilityForceTarget;
import com.ollieread.technomagi.ability.active.ActiveAbilityHarden;
import com.ollieread.technomagi.ability.active.ActiveAbilityInvisibility;
import com.ollieread.technomagi.ability.passive.PassiveAbilityNanites;
import com.ollieread.technomagi.ability.passive.PassiveAbilityNegateFall;
import com.ollieread.technomagi.ability.passive.PassiveAbilityScholarResearch;
import com.ollieread.technomagi.util.ConfigHelper;

public class Abilities
{
    public static IAbilityActive abilityActiveFire;
    public static IAbilityActive abilityActiveFireball;
    public static IAbilityActive abilityActiveHarden;
    public static IAbilityActive abilityActiveInvisibility;
    public static IAbilityActive abilityActiveFlashstep;
    public static IAbilityActive abilityActiveBlink;
    public static IAbilityActive abilityActiveForceTarget;
    public static IAbilityActive abilityActiveForceArea;

    public static IAbilityPassive abilityPassiveScholarResearch;
    public static IAbilityPassive abilityPassiveNanites;
    public static IAbilityPassive abilityPassiveNegateFall;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering abilities");

        if (ConfigHelper.abilityState.get("fire")) {
            abilityActiveFire = new ActiveAbilityFire("fire");
        }
        if (ConfigHelper.abilityState.get("fireball")) {
            abilityActiveFireball = new ActiveAbilityFireball("fireball");
        }
        if (ConfigHelper.abilityState.get("harden")) {
            abilityActiveHarden = new ActiveAbilityHarden("harden");
        }
        if (ConfigHelper.abilityState.get("invisibility")) {
            abilityActiveInvisibility = new ActiveAbilityInvisibility("invisibility");
        }
        if (ConfigHelper.abilityState.get("flashstep")) {
            abilityActiveFlashstep = new ActiveAbilityFlashstep("flashstep");
        }
        if (ConfigHelper.abilityState.get("blink")) {
            abilityActiveBlink = new ActiveAbilityBlink("blink");
        }
        if (ConfigHelper.abilityState.get("knockback")) {
            abilityActiveForceTarget = new ActiveAbilityForceTarget("knockback");
        }
        if (ConfigHelper.abilityState.get("expel")) {
            abilityActiveForceArea = new ActiveAbilityForceArea("expel");
        }
        if (ConfigHelper.abilityState.get("scholarResearch")) {
            abilityPassiveScholarResearch = new PassiveAbilityScholarResearch("scholarResearch");
        }
        if (ConfigHelper.abilityState.get("naniteRegen")) {
            abilityPassiveNanites = new PassiveAbilityNanites("naniteRegen");
        }
        if (ConfigHelper.abilityState.get("negateFall")) {
            abilityPassiveNegateFall = new PassiveAbilityNegateFall("negateFall");
        }
    }
}
