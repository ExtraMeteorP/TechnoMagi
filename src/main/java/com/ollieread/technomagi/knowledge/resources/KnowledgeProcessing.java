package com.ollieread.technomagi.knowledge.resources;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchCrafting;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgeProcessing extends Knowledge
{

    public static IResearchEvent crafting;
    public static IResearchEvent smelting;
    public static IResearchCrafting planks;
    public static IResearchCrafting sticks;
    public static IResearchCrafting iron;
    public static IResearchCrafting gold;
    public static IResearchCrafting stone;
    public static IResearchCrafting glass;
    public static IResearchCrafting brick;
    public static IResearchCrafting torch;

    public KnowledgeProcessing(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_RESOURCES);

        crafting = new ResearchEvent("crafting", getName(), 1, "crafting", 10, 16, new String[] {});
        smelting = new ResearchEvent("smelting", getName(), 1, "smelting", 10, 16, new String[] {});
        planks = new ResearchCrafting("planks", getName(), 1, ItemHelper.block("planks"), 10, 12, new String[] {});
        sticks = new ResearchCrafting("sticks", getName(), 1, ItemHelper.item("stick"), 10, 8, new String[] {});
        iron = new ResearchCrafting("iron", getName(), 1, ItemHelper.item("iron_ingot"), 10, 8, new String[] {});
        gold = new ResearchCrafting("gold", getName(), 1, ItemHelper.item("gold_ingot"), 10, 8, new String[] {});
        stone = new ResearchCrafting("stone", getName(), 1, ItemHelper.block("stone"), 10, 8, new String[] {});
        glass = new ResearchCrafting("glass", getName(), 1, ItemHelper.block("glass"), 10, 8, new String[] {});
        brick = new ResearchCrafting("brick", getName(), 1, ItemHelper.item("brick"), 10, 8, new String[] {});
        torch = new ResearchCrafting("torch", getName(), 1, ItemHelper.block("torch"), 10, 8, new String[] {});
    }

}
