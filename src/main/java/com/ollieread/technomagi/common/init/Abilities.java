package com.ollieread.technomagi.common.init;

import java.util.Map;

import org.apache.logging.log4j.Level;

import com.ollieread.ennds.ability.AbilityRegistry;
import com.ollieread.ennds.ability.IAbilityActive;
import com.ollieread.ennds.ability.IAbilityPassive;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.ability.active.ActiveAbilityAtmosphere;
import com.ollieread.technomagi.ability.active.ActiveAbilityBlink;
import com.ollieread.technomagi.ability.active.ActiveAbilityDrain;
import com.ollieread.technomagi.ability.active.ActiveAbilityFire;
import com.ollieread.technomagi.ability.active.ActiveAbilityFireball;
import com.ollieread.technomagi.ability.active.ActiveAbilityFlashstep;
import com.ollieread.technomagi.ability.active.ActiveAbilityForce;
import com.ollieread.technomagi.ability.active.ActiveAbilityHarden;
import com.ollieread.technomagi.ability.active.ActiveAbilityHarvest;
import com.ollieread.technomagi.ability.active.ActiveAbilityHeal;
import com.ollieread.technomagi.ability.active.ActiveAbilityOverdrive;
import com.ollieread.technomagi.ability.active.ActiveAbilityProjectile;
import com.ollieread.technomagi.ability.active.ActiveAbilityProjectileExothermic;
import com.ollieread.technomagi.ability.active.ActiveAbilityReactive;
import com.ollieread.technomagi.ability.active.ActiveAbilityShield;
import com.ollieread.technomagi.ability.data.AbilityDataNanites;
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
    public static IAbilityActive abilityActiveForce;
    public static IAbilityActive abilityActiveHarvest;
    public static IAbilityActive abilityActiveHeal;
    public static IAbilityActive abilityActiveAtmosphereI;
    public static IAbilityActive abilityActiveProjectile;
    public static IAbilityActive abilityActiveProjectileExothermic;
    public static IAbilityActive abilityActiveAtmosphere;
    public static IAbilityActive abilityActiveShield;
    public static IAbilityActive abilityDrain;
    public static IAbilityActive abilityOverdrive;

    public static IAbilityPassive abilityPassiveScholarResearch;
    public static IAbilityPassive abilityPassiveNanites;
    public static IAbilityPassive abilityPassiveNegateFall;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering abilities");

        AbilityRegistry.registerAbilityData("naniteRegen", AbilityDataNanites.class);

        if (Config.atmosphereEnabled) {
            abilityActiveAtmosphere = new ActiveAbilityAtmosphere("atmosphere");
        }
        if (Config.blinkEnabled) {
            abilityActiveBlink = new ActiveAbilityBlink("blink");
        }
        if (Config.fireEnabled) {
            abilityActiveFire = new ActiveAbilityFire("fire");
        }
        if (Config.fireballEnabled) {
            abilityActiveFireball = new ActiveAbilityFireball("fireball");
        }
        if (Config.flashstepEnabled) {
            abilityActiveFlashstep = new ActiveAbilityFlashstep("flashstep");
        }
        if (Config.forceEnabled) {
            abilityActiveForce = new ActiveAbilityForce("force");
        }
        if (Config.hardenEnabled) {
            abilityActiveHarden = new ActiveAbilityHarden("harden");
        }
        if (Config.harvestEnabled) {
            abilityActiveHarvest = new ActiveAbilityHarvest("harvest");
        }
        if (Config.healEnabled) {
            abilityActiveHeal = new ActiveAbilityHeal("heal");
        }
        if (Config.invisibilityEnabled) {
            abilityActiveInvisibility = new ActiveAbilityHarvest("invisibility");
        }
        if (Config.projectileEnabled) {
            abilityActiveProjectile = new ActiveAbilityProjectile("projectile");
        }
        if (Config.projectileExothermicEnabled) {
            abilityActiveProjectileExothermic = new ActiveAbilityProjectileExothermic("projectileExothermic");
        }
        if (Config.reactiveEnabled) {
            abilityActiveReactive = new ActiveAbilityReactive("reactive");
        }
        if (Config.shieldEnabled) {
            abilityActiveShield = new ActiveAbilityShield("shield");
        }
        if (Config.drainEnabled) {
            abilityDrain = new ActiveAbilityDrain("drain");
        }
        if (Config.overdriveEnabled) {
            abilityOverdrive = new ActiveAbilityOverdrive("overdrive");
        }

        abilityPassiveNanites = new PassiveAbilityNanites("nanites");
    }
}
