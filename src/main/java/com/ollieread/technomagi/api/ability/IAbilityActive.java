package com.ollieread.technomagi.api.ability;

import com.ollieread.technomagi.player.PlayerKnowledge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public interface IAbilityActive {
	
	public String getName();
	
	public ResourceLocation getIcon();
	
	public boolean canUse(PlayerKnowledge charon);
	
	public boolean isAvailable(PlayerKnowledge charon);
	
	public void use(PlayerKnowledge charon);

}
