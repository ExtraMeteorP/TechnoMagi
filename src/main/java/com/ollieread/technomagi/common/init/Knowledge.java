package com.ollieread.technomagi.common.init;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.research.IKnowledge;
import com.ollieread.technomagi.api.research.IResearch;
import com.ollieread.technomagi.knowledge.KnowledgeFire;
import com.ollieread.technomagi.knowledge.KnowledgeSpecialisations;
import com.ollieread.technomagi.knowledge.ResearchFireInFire;
import com.ollieread.technomagi.knowledge.ResearchSpecialisations;

public class Knowledge {
	
	public static IKnowledge knowledgeSpecialisations;
	public static IKnowledge knowledgeFire;
	
	public static IResearch researchSpecialisations;
	public static IResearch researchFireInFire;

	public static void init()
	{
		knowledgeSpecialisations = new KnowledgeSpecialisations("knowledgeSpecialisations");
		knowledgeFire = new KnowledgeFire("knowledgeFire");
		
		TMRegistry.registerKnowledge(knowledgeSpecialisations);
		TMRegistry.registerKnowledge(knowledgeFire);
		
		researchSpecialisations = new ResearchSpecialisations("researchSpecialisations", knowledgeSpecialisations.getName(), 100);
		researchFireInFire = new ResearchFireInFire("researchFireInFire", knowledgeFire.getName(), 25);
		
		TMRegistry.registerResearch(researchSpecialisations);
		TMRegistry.registerResearch(researchFireInFire);
	}
	
}
