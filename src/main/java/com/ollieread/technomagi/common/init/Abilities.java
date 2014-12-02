package com.ollieread.technomagi.common.init;

import java.util.Map;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.ability.IAbilityActive;
import com.ollieread.ennds.ability.IAbilityPassive;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.ability.active.ActiveAbilityProjectile;
import com.ollieread.technomagi.ability.active.ActiveAbilityProjectileExothermic;
import com.ollieread.technomagi.ability.active.ActiveAbilityReactive;
import com.ollieread.technomagi.ability.passive.PassiveAbilityNanites;

public class Abilities
{

    public static Map<String, Boolean> enabledAbilities;

    public static IAbilityActive abilityActiveReactive;
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

        if (Config.reactiveEnabled) {
            abilityActiveReactive = new ActiveAbilityReactive("reactive", Config.reactiveCost);
        }
        if (Config.projectileEnabled) {
            abilityActiveProjectile = new ActiveAbilityProjectile("projectile", Config.projectileCost);
        }
        if (Config.projectileExothermicEnabled) {
            abilityActiveProjectileExothermic = new ActiveAbilityProjectileExothermic("projectileExothermic", Config.projectileExothermicCost);
        }

        abilityPassiveNanites = new PassiveAbilityNanites("nanites");
    }
}
