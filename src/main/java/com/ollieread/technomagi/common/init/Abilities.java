package com.ollieread.technomagi.common.init;

import com.ollieread.technomagi.ability.ActiveAbility1;
import com.ollieread.technomagi.ability.ActiveAbility2;
import com.ollieread.technomagi.ability.ActiveAbility3;
import com.ollieread.technomagi.ability.ActiveAbility4;
import com.ollieread.technomagi.ability.ActiveAbility5;
import com.ollieread.technomagi.ability.ActiveAbility6;
import com.ollieread.technomagi.ability.ActiveAbility7;
import com.ollieread.technomagi.ability.ActiveAbilityFireball;
import com.ollieread.technomagi.ability.PassiveAbilityNanites;
import com.ollieread.technomagi.ability.PassiveAbilityScholarResearch;
import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.ability.AbilityActive;
import com.ollieread.technomagi.api.ability.IAbilityActive;
import com.ollieread.technomagi.api.ability.IAbilityPassive;

public class Abilities {
	
	public static IAbilityActive abilityFireball;
	public static IAbilityActive ability1;
	public static IAbilityActive ability2;
	public static IAbilityActive ability3;
	public static IAbilityActive ability4;
	public static IAbilityActive ability5;
	public static IAbilityActive ability6;
	public static IAbilityActive ability7;
	
	public static IAbilityPassive abilityPassiveScholarResearch;
	public static IAbilityPassive abilityPassiveNanites;

	public static void init()
	{
		abilityFireball = new ActiveAbilityFireball("abilityFireball");
		ability1 = new ActiveAbility1("1");
		ability2 = new ActiveAbility2("2");
		ability3 = new ActiveAbility3("3");
		ability4 = new ActiveAbility4("4");
		ability5 = new ActiveAbility5("5");
		ability6 = new ActiveAbility6("6");
		ability7 = new ActiveAbility7("7");
		
		TMRegistry.registerActiveAbility(abilityFireball);
		TMRegistry.registerActiveAbility(ability1);
		TMRegistry.registerActiveAbility(ability2);
		TMRegistry.registerActiveAbility(ability3);
		TMRegistry.registerActiveAbility(ability4);
		TMRegistry.registerActiveAbility(ability5);
		TMRegistry.registerActiveAbility(ability6);
		TMRegistry.registerActiveAbility(ability7);
		
		abilityPassiveScholarResearch = new PassiveAbilityScholarResearch("abilityPassiveScholarResearch");
		abilityPassiveNanites = new PassiveAbilityNanites("abilityPassiveNanites");
		
		TMRegistry.registerPassiveAbility(abilityPassiveScholarResearch);
		TMRegistry.registerPassiveAbility(abilityPassiveNanites);
	}
	
}
