package com.ollieread.technomagi.api;

import java.util.Collection;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.ability.AbilityHandler;
import com.ollieread.technomagi.api.crafting.CraftingHandler;
import com.ollieread.technomagi.api.electromagnetic.EnergyHandler;
import com.ollieread.technomagi.api.entity.EntityHandler;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.KnowledgeCategory;
import com.ollieread.technomagi.api.knowledge.KnowledgeHandler;
import com.ollieread.technomagi.api.knowledge.research.IResearch;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.api.nanites.NaniteHandler;
import com.ollieread.technomagi.api.scan.ScanHandler;
import com.ollieread.technomagi.api.specialisation.SpecialisationHandler;
import com.ollieread.technomagi.api.time.TimeHandler;

import cpw.mods.fml.common.eventhandler.EventBus;

/**
 * TechnoMagi API
 *
 * This class is here for simplicity, it provides a nice simple one stop shop
 * for registering items with the API as well as a nice way to interact with
 * said items, after they have been registered.
 *
 * @author ollieread
 *
 */
public class TechnomagiApi
{

    /**
     * Prefixes for unlocalised names
     */
    public final static String PREFIX_CATEGORY = "research.category.";
    public final static String PREFIX_KNOWLEDGE = "research.knowledge.";
    public final static String PREFIX_RESEARCH = "research.";
    public final static String PREFIX_SPECIALISATION = "specialisation.";

    /**
     * IEEP idents
     */
    public final static String IDENT_PLAYER = "TechnoMagi_Player";
    public final static String IDENT_ENTITY = "TechnoMagi_Entity";

    /**
     * The event bus used for TechnoMagi events.
     */
    public final static EventBus EVENT_BUS = new EventBus();

    /**
     * Handlers
     *
     * Handles things.
     */
    protected final static KnowledgeHandler HANDLER_KNOWLEDGE = new KnowledgeHandler();
    protected final static SpecialisationHandler HANDLER_SPECIALISATION = new SpecialisationHandler();
    protected final static EntityHandler HANDLER_ENTITY = new EntityHandler();
    protected final static ScanHandler HANDLER_SCAN = new ScanHandler();
    protected final static AbilityHandler HANDLER_ABILITY = new AbilityHandler();
    protected final static CraftingHandler HANDLER_CRAFTING = new CraftingHandler();
    protected final static EnergyHandler HANDLER_ENERGY = new EnergyHandler();
    protected final static NaniteHandler HANDLER_NANITE = new NaniteHandler();
    protected final static TimeHandler HANDLER_TIME = new TimeHandler();

    /**
     * Retrieve the knowledge handler.
     *
     * @see KnowledgeHandler
     * @return The instance of KnowledgeHandler.
     */
    public static KnowledgeHandler knowledge()
    {
        return HANDLER_KNOWLEDGE;
    }

    /**
     * Retrieve the specialisation handler.
     *
     * @see SpecialisationHandler
     * @return
     */
    public static SpecialisationHandler specialisation()
    {
        return HANDLER_SPECIALISATION;
    }

    /**
     * Retrieve the entity handler.
     *
     * @see EntityHandler
     * @return
     */
    public static EntityHandler entity()
    {
        return HANDLER_ENTITY;
    }

    /**
     * Retrieve the scan handler.
     *
     * @see ScanHandler
     * @return
     */
    public static ScanHandler scan()
    {
        return HANDLER_SCAN;
    }

    /**
     * Retrieve the ability handler.
     *
     * @see AbilityHandler
     * @return
     */
    public static AbilityHandler ability()
    {
        return HANDLER_ABILITY;
    }

    public static CraftingHandler crafting()
    {
        return HANDLER_CRAFTING;
    }

    public static EnergyHandler energy()
    {
        return HANDLER_ENERGY;
    }

    public static NaniteHandler nanite()
    {
        return HANDLER_NANITE;
    }

    public static TimeHandler time()
    {
        return HANDLER_TIME;
    }

    /**
     * Create and add a basic knowledge category.
     *
     * @param name The name for the knowledge category, which the category shall
     *        be referenced by here on out.
     * @param icon An instance of ResourceLocation pointing to the icon for the
     *        knowledge, typically a 32 x 32 image.
     * @return The knowledge category that was added, or null if it failed.
     */
    public static KnowledgeCategory addKnowledgeCategory(String name, ResourceLocation icon)
    {
        return knowledge().addCategory(new KnowledgeCategory(name, icon));
    }

    /**
     * Add an already created knowledge category.
     *
     * @see KnowledgeHandler#addCategory(KnowledgeCategory)
     * @param category
     * @return
     */
    public static KnowledgeCategory addKnowledgeCategory(KnowledgeCategory category)
    {
        return knowledge().addCategory(category);
    }

    /**
     * @see KnowledgeHandler#getCategory(String)
     * @param name
     * @return
     */
    public static KnowledgeCategory getKnowledgeCategory(String name)
    {
        return knowledge().getCategory(name);
    }

    /**
     * @see KnowledgeHandler#getCategories()
     * @return
     */
    public static Collection<KnowledgeCategory> getKnowledgeCategories()
    {
        return knowledge().getCategories();
    }

    /**
     * Create and add a basic knowledge topic.
     *
     * @param name The name for the knowledge topic, which the knowledge shall
     *        be referenced by here on out.
     * @param icon An instance of ResourceLocation pointing to the icon for the
     *        knowledge, typically a 32 x 32 image.
     * @param category The name of the category that the knowledge should be
     *        assigned to. Category must already have been created and
     *        registered.
     * @return The knowledge topic that was added, or null if it failed.
     */
    public static Knowledge addKnowledge(String name, ResourceLocation icon, String category)
    {
        return knowledge().addKnowledge(new Knowledge(name, icon, category));
    }

    /**
     * Add an already created knowledge topic.
     *
     * @see KnowledgeHandler#addKnowledge(Knowledge)
     * @param knowledge
     * @return
     */
    public static Knowledge addKnowledge(Knowledge knowledge)
    {
        return knowledge().addKnowledge(knowledge);
    }

    /**
     * @see KnowledgeHandler#getKnowledge(String)
     * @param name
     * @return
     */
    public static Knowledge getKnowledge(String name)
    {
        return knowledge().getKnowledge(name);
    }

    /**
     * @see KnowledgeHandler#getKnowledge()
     * @return
     */
    public static Collection<Knowledge> getKnowledge()
    {
        return knowledge().getKnowledge();
    }

    /**
     * Create and add a basic piece of research.
     *
     * This method returns an instance of {@link Research} which implements
     * {@link IResearch} and saves creating your own classes. Since only the
     * name is stored outside of the instance, this means you can configure the
     * item further without having to worry about re-registering.
     *
     * @see Research
     * @param name
     * @return
     */
    public static Research addResearch(String name, String knowledge)
    {
        Research research = new Research(name);
        research.setKnowledge(knowledge);
        addResearch(research);

        return research;
    }

    /**
     * Add an already created research item to the registry. Returns the object
     * for further processing should it be required.
     *
     * @see IResearch
     * @see KnowledgeHandler#addResearch(IResearch)
     * @param research
     * @return
     */
    public static IResearch addResearch(IResearch research)
    {
        return knowledge().addResearch(research);
    }

    /**
     * Retrieve a piece of research by its name.
     *
     * @see KnowledgeHandler#getResearch(String)
     * @param name
     * @return
     */
    public static IResearch getResearch(String name)
    {
        return knowledge().getResearch(name);
    }

}
