package com.ollieread.technomagi.api.knowledge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.helpers.EntityHelper;
import com.ollieread.technomagi.api.knowledge.research.IResearch;

/**
 * Knowledge Handler
 * 
 * This class handles the registration of knowledge categories, knowledge,
 * research and the firing/unlocking of knowledge and research.
 * 
 * @author ollieread
 *
 */
public class KnowledgeHandler
{

    protected static Map<String, KnowledgeCategory> categoryList = new LinkedHashMap<String, KnowledgeCategory>();
    protected static Map<String, Knowledge> knowledgeList = new LinkedHashMap<String, Knowledge>();
    protected static Map<String, List<String>> categoryKnowledgeList = new LinkedHashMap<String, List<String>>();
    protected static Map<String, IResearch> researchList = new LinkedHashMap<String, IResearch>();

    protected Random rand = new Random();

    /**
     * Register a knowledge category.
     * 
     * @see KnowledgeCategory
     * @param category The instance of KnowledgeCategory to register.
     * @return
     */
    public KnowledgeCategory addCategory(KnowledgeCategory category)
    {
        if (!categoryList.containsKey(category.getName()) && !categoryList.containsValue(category)) {
            categoryList.put(category.getName(), category);
            categoryKnowledgeList.put(category.getName(), new ArrayList<String>());

            return category;
        }

        return null;
    }

    /**
     * Retrieve a knowledge category.
     * 
     * Find and retrieve a category for the provided name.
     * 
     * @param name
     * @return
     */
    public KnowledgeCategory getCategory(String name)
    {
        if (categoryList.containsKey(name)) {
            return categoryList.get(name);
        }

        return null;
    }

    /**
     * Get a full list of all registered categories.
     * 
     * @return
     */
    public Collection<KnowledgeCategory> getCategories()
    {
        return categoryList.values();
    }

    /**
     * Register a knowledge topic.
     * 
     * @see Knowledge
     * @param knowledge The instance of Knowledge to register.
     * @return
     */
    public Knowledge addKnowledge(Knowledge knowledge)
    {
        if (!knowledgeList.containsKey(knowledge.getName()) && !knowledgeList.containsValue(knowledge) && categoryList.containsKey(knowledge.getCategory())) {
            knowledgeList.put(knowledge.getName(), knowledge);
            categoryKnowledgeList.get(knowledge.getCategory()).add(knowledge.getName());

            return knowledge;
        }

        return null;
    }

    /**
     * Retrieve a knowledge topic.
     * 
     * Find and retrieve a knowledge topic for the provided name.
     * 
     * @param name
     * @return
     */
    public Knowledge getKnowledge(String name)
    {
        if (knowledgeList.containsKey(name)) {
            return knowledgeList.get(name);
        }

        return null;
    }

    /**
     * Get a full list of all knowledge topics.
     * 
     * @return
     */
    public Collection<Knowledge> getKnowledge()
    {
        return knowledgeList.values();
    }

    /**
     * Retrieve a list of knowledge names for the provided category.
     * 
     * @param name
     * @return
     */
    public List<String> getKnowledgeNamesForCategory(String name)
    {
        if (categoryKnowledgeList.containsKey(name)) {
            return categoryKnowledgeList.get(name);
        }

        return null;
    }

    /**
     * Retrieve a list of knowledge topics for the provided category.
     * 
     * @param name
     * @return
     */
    public List<Knowledge> getKnowledgeForCategory(String name)
    {
        List<String> knowledgeNames = getKnowledgeNamesForCategory(name);

        if (knowledgeNames != null) {
            List<Knowledge> knowledge = new ArrayList<Knowledge>();

            for (String knowledgeName : knowledgeNames) {
                knowledge.add(getKnowledge(knowledgeName));
            }

            return knowledge;
        }

        return null;
    }

    /**
     * Register a piece of research.
     * 
     * @param research
     * @return
     */
    public IResearch addResearch(IResearch research)
    {
        if (!researchList.containsKey(research.getName()) && !researchList.containsValue(research)) {
            researchList.put(research.getName(), research);

            return research;
        }

        return null;
    }

    /**
     * Retrieve a piece of research.
     * 
     * @param name
     * @return
     */
    public IResearch getResearch(String name)
    {
        if (researchList.containsKey(name)) {
            return researchList.get(name);
        }

        return null;
    }

    /**
     * Perform a piece of research.
     * 
     * @see PlayerKnowledge#performResearch(IResearch, Knowledge)
     * @param player
     * @param research
     */
    public void performResearch(EntityPlayer player, IResearch research)
    {
        Knowledge knowledge = TechnomagiApi.getKnowledge(research.getKnowledge());
        PlayerTechnomagi playerTechnomagi = EntityHelper.getPlayerTechnomagi(player);

        if (playerTechnomagi != null) {
            playerTechnomagi.performResearch(research, knowledge);
        }
    }
}