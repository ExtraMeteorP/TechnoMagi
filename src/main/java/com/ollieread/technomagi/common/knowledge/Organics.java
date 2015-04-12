package com.ollieread.technomagi.common.knowledge;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.KnowledgeCategory;
import com.ollieread.technomagi.common.knowledge.organics.BasicOrganics;
import com.ollieread.technomagi.common.knowledge.organics.Bionics;
import com.ollieread.technomagi.common.knowledge.organics.DNAManipulation;
import com.ollieread.technomagi.common.knowledge.organics.OrganicTechnology;
import com.ollieread.technomagi.common.knowledge.organics.Petrification;
import com.ollieread.technomagi.common.knowledge.organics.TechnorganicMaterials;
import com.ollieread.technomagi.util.ResourceHelper;

public class Organics
{

    public static KnowledgeCategory category;

    public static BasicOrganics basicOrganics;
    public static Petrification petrification;
    public static TechnorganicMaterials technorganicMaterials;
    public static OrganicTechnology organicTechnology;
    public static Bionics bionics;
    public static DNAManipulation dnaManipulation;

    public static void knowledge()
    {
        category = TechnomagiApi.addKnowledgeCategory("organics", ResourceHelper.texture("knowledge/organics.png"));
        category.setLocation(0, 30);

        basicOrganics = new BasicOrganics();
        basicOrganics.setLocation(0, 0);

        petrification = new Petrification();
        petrification.setLocation(0, -30);

        technorganicMaterials = new TechnorganicMaterials();
        technorganicMaterials.setLocation(27, 15);

        organicTechnology = new OrganicTechnology();
        organicTechnology.setLocation(54, 30);

        bionics = new Bionics();
        bionics.setLocation(81, 45);

        dnaManipulation = new DNAManipulation();
        dnaManipulation.setLocation(-27, -15);

        petrification.addPrerequisite(basicOrganics.getName());
        technorganicMaterials.addPrerequisite(basicOrganics.getName());
        organicTechnology.addPrerequisite(technorganicMaterials.getName());
        bionics.addPrerequisite(organicTechnology.getName());
        dnaManipulation.addPrerequisite(basicOrganics.getName());
    }

    public static void mappings()
    {

    }

    public static void recipes()
    {

    }

}
