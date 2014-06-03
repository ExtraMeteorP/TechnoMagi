package com.ollieread.technomagi.api.research;

import net.minecraft.util.ResourceLocation;

public class Knowledge implements IKnowledge {
	
	protected String knowledgeName;
	protected ResourceLocation knowledgeIcon;
	
	public Knowledge(String name)
	{
		knowledgeName = name;
		knowledgeIcon = new ResourceLocation("technomagi", "textures/knowledge/" + name + ".png");
	}
	
	public Knowledge(String name, ResourceLocation icon)
	{
		knowledgeName = name;
		knowledgeIcon = icon;
	}

	@Override
	public String getName()
	{
		return knowledgeName;
	}

	@Override
	public ResourceLocation getIcon()
	{
		return knowledgeIcon;
	}

}
