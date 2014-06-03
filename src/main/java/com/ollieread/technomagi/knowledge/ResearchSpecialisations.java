package com.ollieread.technomagi.knowledge;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.event.TMEvent.SpecialisationChosenEvent;
import com.ollieread.technomagi.api.research.Research;
import com.ollieread.technomagi.player.PlayerKnowledge;

public class ResearchSpecialisations extends Research {

	public ResearchSpecialisations(String name, String knowledge, int progress)
	{
		super(name, knowledge, progress);
	}

	@Override
	public int getEvent()
	{
		return TMRegistry.EVENT_SPECIALISATION;
	}

	@Override
	public boolean canPerform(PlayerKnowledge charon)
	{
		return true;
	}

	@Override
	public boolean isRepeating()
	{
		return true;
	}

}
