package com.ollieread.technomagi.api.ability;

import net.minecraft.util.ResourceLocation;

public abstract class AbilityActive implements IAbilityActive {

	public ResourceLocation abilityIcon;
	public String abilityName;
	
	public AbilityActive(String name)
	{
		abilityName = name;
		abilityIcon = new ResourceLocation("technomagi", "textures/abilities/" + name + ".png");
	}
	
	public AbilityActive(String name, ResourceLocation icon)
	{
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
