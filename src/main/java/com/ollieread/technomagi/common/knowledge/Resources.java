package com.ollieread.technomagi.common.knowledge;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.KnowledgeCategory;
import com.ollieread.technomagi.common.knowledge.resources.AdvancedResources;
import com.ollieread.technomagi.common.knowledge.resources.AmbientResourceConstruction;
import com.ollieread.technomagi.common.knowledge.resources.BasicResources;
import com.ollieread.technomagi.common.knowledge.resources.BetterResources;
import com.ollieread.technomagi.common.knowledge.resources.EnhancedCrafting;
import com.ollieread.technomagi.common.knowledge.resources.Etherium;
import com.ollieread.technomagi.common.knowledge.resources.Liquids;
import com.ollieread.technomagi.common.knowledge.resources.Optimisation;
import com.ollieread.technomagi.common.knowledge.resources.ResourceManipulation;
import com.ollieread.technomagi.common.knowledge.resources.Storage;
import com.ollieread.technomagi.util.ResourceHelper;

public class Resources
{

    public static KnowledgeCategory category;

    public static BasicResources basicResources;
    public static Etherium etherium;
    public static BetterResources betterResources;
    public static Optimisation optimisation;
    public static Storage storage;
    public static Liquids liquids;
    public static AmbientResourceConstruction ambientResourceConstruction;
    public static EnhancedCrafting enhancedCrafting;
    public static AdvancedResources advancedResources;
    public static ResourceManipulation resourceManipulation;

    public static void knowledge()
    {
        category = TechnomagiApi.addKnowledgeCategory("resources", ResourceHelper.texture("knowledge/resources.png"));
        category.setLocation(27, 15);

        basicResources = new BasicResources();
        basicResources.setLocation(0, 0);

        etherium = new Etherium();
        etherium.setLocation(0, -30);

        betterResources = new BetterResources();
        betterResources.setLocation(27, 15);

        advancedResources = new AdvancedResources();
        advancedResources.setLocation(54, 30);

        ambientResourceConstruction = new AmbientResourceConstruction();
        ambientResourceConstruction.setLocation(0, 30);

        enhancedCrafting = new EnhancedCrafting();
        enhancedCrafting.setLocation(0, 60);

        storage = new Storage();
        storage.setLocation(-27, 15);

        liquids = new Liquids();
        liquids.setLocation(-54, 30);

        optimisation = new Optimisation();
        optimisation.setLocation(54, 0);

        resourceManipulation = new ResourceManipulation();
        resourceManipulation.setLocation(54, 60);

        etherium.addPrerequisite(basicResources.getName());
        betterResources.addPrerequisite(basicResources.getName());
        ambientResourceConstruction.addPrerequisite(basicResources.getName());
        enhancedCrafting.addPrerequisite(ambientResourceConstruction.getName());
        advancedResources.addPrerequisite(betterResources.getName());
        storage.addPrerequisite(basicResources.getName());
        liquids.addPrerequisite(storage.getName());
        optimisation.addPrerequisite(betterResources.getName());
        resourceManipulation.addPrerequisite(advancedResources.getName());
    }

    public static void mappings()
    {
        basicResources.mappings();
        etherium.mappings();
        betterResources.mappings();
        advancedResources.mappings();
        ambientResourceConstruction.mappings();
        enhancedCrafting.mappings();
        storage.mappings();
        liquids.mappings();
        optimisation.mappings();
        resourceManipulation.mappings();
    }

    public static void recipes()
    {

    }

}
