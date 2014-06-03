package com.ollieread.technomagi.api.research;

import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public interface IResearch {

	public String getName();
	
	public ResourceLocation getIcon();
	
	public String getKnowledge();
	
	public int getProgress();
	
	public int getEvent();
	
	public boolean isRepeating();
	
	public boolean canPerform(PlayerKnowledge charon);
	
}
