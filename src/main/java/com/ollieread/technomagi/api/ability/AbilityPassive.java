package com.ollieread.technomagi.api.ability;

import net.minecraft.util.ResourceLocation;

public abstract class AbilityPassive<E> implements IAbilityPassive<E> {

	public ResourceLocation abilityIcon;
	public String abilityName;
	
	public AbilityPassive(String name)
	{
		abilityName = name;
		abilityIcon = new ResourceLocation("technomagi", "textures/ability/" + name + ".png");
	}
	
	public AbilityPassive(String name, ResourceLocation icon)
	{
		abilityName = name;
		abilityIcon = icon;
	}

	@Override
	public ResourceLocation getIcon()
	{
		return abilityIcon;
	}
	
	@Override
	public String getName()
	{
		return abilityName;
	}

}
