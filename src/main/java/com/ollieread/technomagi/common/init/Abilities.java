package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.ability.IAbilityActive;
import com.ollieread.ennds.ability.IAbilityPassive;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.ability.active.ActiveAbilityEndothermic;
import com.ollieread.technomagi.ability.active.ActiveAbilityExothermic;
import com.ollieread.technomagi.ability.active.ActiveAbilityProjectile;
import com.ollieread.technomagi.ability.active.ActiveAbilityProjectileExothermic;
import com.ollieread.technomagi.ability.passive.PassiveAbilityNanites;

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
    public static IAbilityActive abilityActiveProjectile;
    public static IAbilityActive abilityActiveProjectileExothermic;

    public static IAbilityPassive abilityPassiveScholarResearch;
    public static IAbilityPassive abilityPassiveNanites;
    public static IAbilityPassive abilityPassiveNegateFall;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering abilities");

        abilityActiveExothermic = new ActiveAbilityExothermic("exothermic");
        abilityActiveEndothermic = new ActiveAbilityEndothermic("endothermic");
        abilityActiveProjectile = new ActiveAbilityProjectile("projectile");
        abilityActiveProjectileExothermic = new ActiveAbilityProjectileExothermic("projectileExothermic");

        abilityPassiveNanites = new PassiveAbilityNanites("nanites");
    }
}
