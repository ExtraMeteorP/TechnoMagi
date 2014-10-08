package com.ollieread.technomagi.common.init;

import java.util.Map;

import net.minecraftforge.common.config.Configuration;

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

    public static Map<String, Boolean> enabledAbilities;

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
        Configuration config = TechnoMagi.config;

        TechnoMagi.logger.log(Level.INFO, "Initiating & registering abilities");

        if (config.getBoolean("exothermic", "Ability States", true, null)) {
            abilityActiveExothermic = new ActiveAbilityExothermic("exothermic", config.getInt("exothermic", "Ability Cost", 10, 1, 100, null));
        }
        if (config.getBoolean("endothermic", "Ability States", true, null)) {
            abilityActiveEndothermic = new ActiveAbilityEndothermic("endothermic", config.getInt("endothermic", "Ability Cost", 10, 1, 100, null));
        }
        if (config.getBoolean("projectile", "Ability States", true, null)) {
            abilityActiveProjectile = new ActiveAbilityProjectile("projectile", config.getInt("projectile", "Ability Cost", 5, 1, 100, null));
        }
        if (config.getBoolean("projectileExothermic", "Ability States", true, null)) {
            abilityActiveProjectileExothermic = new ActiveAbilityProjectileExothermic("projectileExothermic", config.getInt("projectileExothermic", "Ability Cost", 7, 1, 100, null));
        }

        abilityPassiveNanites = new PassiveAbilityNanites("nanites");
    }
}
