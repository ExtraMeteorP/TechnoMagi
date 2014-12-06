package com.ollieread.technomagi.knowledge;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeLifeII extends Knowledge
{

    public static IResearchEvent breedSheep;
    public static IResearchEvent breedCow;
    public static IResearchEvent breedPig;
    public static IResearchEvent breedChicken;
    public static IResearchEvent birthSheep;
    public static IResearchEvent birthCow;
    public static IResearchEvent birthPig;
    public static IResearchEvent birthChicken;

    public KnowledgeLifeII(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        breedSheep = new ResearchEvent("lifeBreedSheep", getName(), 10, "breedingSheep", false, 1, new String[] { "life" });
        breedCow = new ResearchEvent("lifeBreedCow", getName(), 10, "breedingCow", false, 1, new String[] { "life" });
        breedPig = new ResearchEvent("lifeBreedPig", getName(), 10, "breedingPig", false, 1, new String[] { "life" });
        breedChicken = new ResearchEvent("lifeBreedChicken", getName(), 10, "breedingChicken", false, 1, new String[] { "life" });
        birthSheep = new ResearchEvent("lifeBirthSheep", getName(), 15, "birthSheep", false, 1, new String[] { "life" });
        birthCow = new ResearchEvent("lifeBirthCow", getName(), 15, "birthCow", false, 1, new String[] { "life" });
        birthPig = new ResearchEvent("lifeBirthPig", getName(), 15, "birthPig", false, 1, new String[] { "life" });
        birthChicken = new ResearchEvent("lifeBirthChicken", getName(), 15, "birthChicken", false, 1, new String[] { "life" });
    }

}
