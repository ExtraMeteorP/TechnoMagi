package com.ollieread.technomagi.common.knowledge;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.KnowledgeCategory;
import com.ollieread.technomagi.common.knowledge.technomancy.Adaption;
import com.ollieread.technomagi.common.knowledge.technomancy.AdaptionEnvironment;
import com.ollieread.technomagi.common.knowledge.technomancy.AdvancedMachines;
import com.ollieread.technomagi.common.knowledge.technomancy.BasicMachines;
import com.ollieread.technomagi.common.knowledge.technomancy.BasicTechnomancy;
import com.ollieread.technomagi.common.knowledge.technomancy.Dimensional;
import com.ollieread.technomagi.common.knowledge.technomancy.Exoskeleton;
import com.ollieread.technomagi.common.knowledge.technomancy.Restoration;
import com.ollieread.technomagi.common.knowledge.technomancy.RestorationLiving;
import com.ollieread.technomagi.common.knowledge.technomancy.RestorationPhysical;
import com.ollieread.technomagi.common.knowledge.technomancy.TechnologicalConstructs;
import com.ollieread.technomagi.common.knowledge.technomancy.Teleportation;
import com.ollieread.technomagi.common.knowledge.technomancy.TeleportationPhaseJumping;
import com.ollieread.technomagi.common.knowledge.technomancy.TeleportationSubstitution;
import com.ollieread.technomagi.common.knowledge.technomancy.TeleportationZeroShift;
import com.ollieread.technomagi.common.knowledge.technomancy.Transmutation;
import com.ollieread.technomagi.util.ResourceHelper;

public class Technomancy
{

    public static KnowledgeCategory category;

    public static BasicTechnomancy basicTechnomancy;
    public static BasicMachines basicMachines;
    public static Exoskeleton exoskeleton;
    public static Restoration restoration;
    public static RestorationLiving restorationLiving;
    public static RestorationPhysical restorationPhysical;
    public static TechnologicalConstructs technologicalConstructs;
    public static Adaption adaption;
    public static AdaptionEnvironment adaptionEnvironment;
    public static Teleportation teleportation;
    public static TeleportationPhaseJumping teleportationPhaseJumping;
    public static TeleportationSubstitution teleportationSubstituting;
    public static TeleportationZeroShift teleportationZeroShift;
    public static Transmutation transmutation;
    public static AdvancedMachines advancedMachines;
    public static Dimensional dimensional;

    public static void knowledge()
    {
        category = TechnomagiApi.addKnowledgeCategory("technomancy", ResourceHelper.texture("knowledge/technomancy.png"));
        category.setLocation(0, 0);

        basicTechnomancy = new BasicTechnomancy();
        basicTechnomancy.setLocation(0, 0);

        basicMachines = new BasicMachines();
        basicMachines.addPrerequisite(basicTechnomancy.getName()).setLocation(0, -30);

        advancedMachines = new AdvancedMachines();
        advancedMachines.addPrerequisite(basicMachines.getName()).setLocation(0, -60);

        technologicalConstructs = new TechnologicalConstructs();
        technologicalConstructs.addPrerequisite(advancedMachines.getName()).setLocation(0, -90);

        teleportation = new Teleportation();
        teleportation.addPrerequisite(basicTechnomancy.getName()).setLocation(27, 15);

        teleportationZeroShift = new TeleportationZeroShift();
        teleportationZeroShift.addPrerequisite(teleportation.getName()).setLocation(54, 30);

        teleportationSubstituting = new TeleportationSubstitution();
        teleportationSubstituting.addPrerequisite(teleportationZeroShift.getName()).setLocation(81, 45);

        teleportationPhaseJumping = new TeleportationPhaseJumping();
        teleportationPhaseJumping.addPrerequisite(teleportationSubstituting.getName()).setLocation(54, 60);

        dimensional = new Dimensional();
        dimensional.addPrerequisite(teleportation.getName()).setLocation(27, 45);

        restoration = new Restoration();
        restoration.addPrerequisite(basicTechnomancy.getName()).setLocation(-27, -15);

        restorationLiving = new RestorationLiving();
        restorationLiving.addPrerequisite(restoration.getName()).setLocation(-54, -30);

        restorationPhysical = new RestorationPhysical();
        restorationPhysical.addPrerequisite(restorationLiving.getName()).setLocation(-81, -45);

        transmutation = new Transmutation();
        transmutation.addPrerequisite(basicTechnomancy.getName()).setLocation(0, 30);

        adaption = new Adaption();
        adaption.addPrerequisite(basicTechnomancy.getName()).setLocation(-27, 15);

        adaptionEnvironment = new AdaptionEnvironment();
        adaptionEnvironment.addPrerequisite(adaption.getName()).setLocation(-54, 30);

        exoskeleton = new Exoskeleton();
        exoskeleton.addPrerequisite(adaptionEnvironment.getName()).setLocation(-81, 45);
    }

    public static void mappings()
    {
        BasicTechnomancy.mappings();
        basicMachines.mappings();
        Exoskeleton.mappings();
        Restoration.mappings();
        RestorationLiving.mappings();
        RestorationPhysical.mappings();
        TechnologicalConstructs.mappings();
        Adaption.mappings();
        AdaptionEnvironment.mappings();
        Teleportation.mappings();
        TeleportationPhaseJumping.mappings();
        TeleportationSubstitution.mappings();
        TeleportationZeroShift.mappings();
        Transmutation.mappings();
        AdvancedMachines.mappings();
        Dimensional.mappings();
    }

    public static void recipes()
    {

    }

}
