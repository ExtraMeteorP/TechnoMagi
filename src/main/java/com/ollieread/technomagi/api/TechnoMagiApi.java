package com.ollieread.technomagi.api;

import java.util.Collection;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.KnowledgeCategory;
import com.ollieread.technomagi.api.knowledge.KnowledgeHandler;

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
public class TechnoMagiApi
{

    /**
     * Prefixes for unlocalised names
     */
    public final static String PREFIX_CATEGORY = "research.category";
    public final static String PREFIX_KNOWLEDGE = "research.knowledge";
    public final static String PREFIX_RESEARCH = "research.";

    /**
     * IEEP idents
     */
    public final static String IDENT_KNOWLEDGE = "TechnoMagi_Knowledge";

    /**
     * The event bus used for TechnoMagi events.
     */
    public final static EventBus EVENT_BUS = new EventBus();
    /**
     * Knowledge handler.
     * 
     * Handles all knowledge and research related activity.
     * 
     * @see KnowledgeHandler
     */
    protected final static KnowledgeHandler HANDLER_KNOWLEDGE = new KnowledgeHandler();

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

}
