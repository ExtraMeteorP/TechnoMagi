package com.ollieread.technomagi.api.event;

import com.ollieread.technomagi.api.ISpecialisation;
import com.ollieread.technomagi.api.research.IResearch;
import com.ollieread.technomagi.player.PlayerKnowledge;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

public class TMEvent extends Event {
	
	public final Entity entity;
	public final PlayerKnowledge charon;
	public final boolean researchFired;

    public TMEvent(Entity entity)
    {
        this.entity = entity;
		this.charon = PlayerKnowledge.get((EntityPlayer) entity);
		this.researchFired = false;
    }
    
    public TMEvent(Entity entity, boolean researchFired)
    {
    	this.entity = entity;
		this.charon = PlayerKnowledge.get((EntityPlayer) entity);
		this.researchFired = researchFired;
    }
    
    public static class SpecialisationChosenEvent extends TMEvent {
    	
    	public final ISpecialisation specialisation;
    	
    	public SpecialisationChosenEvent(Entity entity, ISpecialisation specialisation)
    	{
    		super(entity);
    		this.specialisation = specialisation;
    	}
    }
    
    public static class ResearchCompleteEvent extends TMEvent {
    	
    	public final IResearch research;
    	
    	public ResearchCompleteEvent(Entity entity, IResearch research)
    	{
    		super(entity, true);
    		this.research = research;
    	}
    	
    }
    
    @Cancelable
    public static class ResearchProgressEvent extends TMEvent {

		public final IResearch research;
		public final int current;
		public final int progress;
		public int modifier = 0;
    	
    	public ResearchProgressEvent(Entity entity, IResearch research, int current, int progress)
    	{
			super(entity, true);			
			this.research = research;
			this.current = current;
			this.progress = progress;
		}
    	
    }

}
