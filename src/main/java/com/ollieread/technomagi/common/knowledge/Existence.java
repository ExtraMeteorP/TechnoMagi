package com.ollieread.technomagi.common.knowledge;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.KnowledgeCategory;
import com.ollieread.technomagi.common.knowledge.existence.ArtificialLife;
import com.ollieread.technomagi.common.knowledge.existence.Automatons;
import com.ollieread.technomagi.common.knowledge.existence.BasicExistence;
import com.ollieread.technomagi.common.knowledge.existence.Regeneration;
import com.ollieread.technomagi.common.knowledge.existence.RegenerationAbsorbing;
import com.ollieread.technomagi.common.knowledge.existence.RegenerationAdaptive;
import com.ollieread.technomagi.common.knowledge.existence.RegenerationEnergy;
import com.ollieread.technomagi.util.ResourceHelper;

public class Existence
{

    public static KnowledgeCategory category;

    public static BasicExistence basicExistence;
    public static Regeneration regeneration;
    public static RegenerationAbsorbing regenerationAbsorbing;
    public static RegenerationAdaptive regenerationAdaptive;
    public static RegenerationEnergy regenerationEnergy;
    public static Automatons automatons;
    public static ArtificialLife artificialLife;

    public static void knowledge()
    {
        category = TechnomagiApi.addKnowledgeCategory("existence", ResourceHelper.texture("knowledge/existence.png"));
        category.setLocation(-27, 15);

        basicExistence = new BasicExistence();
        basicExistence.setLocation(0, 0);

        regeneration = new Regeneration();
        regeneration.setLocation(0, -30);

        regenerationAbsorbing = new RegenerationAbsorbing();
        regenerationAbsorbing.setLocation(0, -60);

        regenerationAdaptive = new RegenerationAdaptive();
        regenerationAdaptive.setLocation(0, -90);

        regenerationEnergy = new RegenerationEnergy();
        regenerationEnergy.setLocation(0, -120);

        automatons = new Automatons();
        automatons.setLocation(-27, 15);

        artificialLife = new ArtificialLife();
        artificialLife.setLocation(-54, 30);

        regeneration.addPrerequisite(basicExistence.getName());
        regenerationAbsorbing.addPrerequisite(regeneration.getName());
        regenerationAdaptive.addPrerequisite(regenerationAbsorbing.getName());
        regenerationEnergy.addPrerequisite(regenerationAdaptive.getName());
        automatons.addPrerequisite(basicExistence.getName());
        artificialLife.addPrerequisite(automatons.getName());
    }

    public static void mappings()
    {

    }

}
