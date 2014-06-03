package com.ollieread.technomagi.ability;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.ability.AbilityPassive;
import com.ollieread.technomagi.api.event.TMEvent.ResearchProgressEvent;
import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.player.PlayerKnowledge;

public class PassiveAbilityScholarResearch extends AbilityPassive<ResearchProgressEvent> {

	public PassiveAbilityScholarResearch(String name)
	{
		super(name);
	}

	@Override
	public int getEvent()
	{
		return TMRegistry.EVENT_RESEARCH_PROGRESS;
	}

	@Override
	public boolean canUse(PlayerKnowledge charon)
	{
		return true;
	}

	@Override
	public boolean isAvailable(PlayerKnowledge charon)
	{		
		return charon.isSpecialisation(Specialisations.specScholar.getName());
	}

	@Override
	public void use(ResearchProgressEvent event, PlayerKnowledge charon)
	{
		int progress = event.progress;
		int current = event.current;
		int modifier = 0;
		int divide = 1;
		
		if(progress < 100) {
			if(current > 50) {
				divide = 2;
			}
			if(progress < 10) {
				modifier = (int) (progress * (0.35 / divide));
			} else if(progress < 50) {
				modifier = (int) (progress * (0.25 / divide));
			} else if(progress < 100) {
				modifier = (int) (progress * (0.10 / divide));
			}
		}
		
		event.modifier = (int) Math.round(modifier);
	}

}
