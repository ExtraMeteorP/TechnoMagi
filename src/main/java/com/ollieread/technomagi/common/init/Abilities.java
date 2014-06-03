package com.ollieread.technomagi.common.init;

import com.ollieread.technomagi.ability.ActiveAbilityFireball;
import com.ollieread.technomagi.ability.PassiveAbilityNanites;
import com.ollieread.technomagi.ability.PassiveAbilityScholarResearch;
import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.ability.IAbilityActive;
import com.ollieread.technomagi.api.ability.IAbilityPassive;

public class Abilities
{

    public static IAbilityActive abilityFireball;

    public static IAbilityPassive abilityPassiveScholarResearch;
    public static IAbilityPassive abilityPassiveNanites;

    public static void init()
    {
        abilityFireball = new ActiveAbilityFireball("abilityFireball");

        TMRegistry.registerActiveAbility(abilityFireball);

        abilityPassiveScholarResearch = new PassiveAbilityScholarResearch("abilityPassiveScholarResearch");
        abilityPassiveNanites = new PassiveAbilityNanites("abilityPassiveNanites");

        TMRegistry.registerPassiveAbility(abilityPassiveScholarResearch);
        TMRegistry.registerPassiveAbility(abilityPassiveNanites);
    }

}
