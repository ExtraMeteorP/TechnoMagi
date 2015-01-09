package com.ollieread.technomagi.knowledge.technology;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchCrafting;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgeEfficiency extends Knowledge
{

    public static IResearchEvent processing;
    public static IResearchEvent metabolism;

    public static IResearchEvent woodPickaxe;
    public static IResearchEvent woodAxe;
    public static IResearchEvent stonePickaxe;
    public static IResearchEvent stoneAxe;

    public static IResearchCrafting ironPickaxe;
    public static IResearchCrafting ironAxe;
    public static IResearchCrafting ironShovel;
    public static IResearchCrafting ironSword;

    public static IResearchCrafting diamondPickaxe;
    public static IResearchCrafting diamondAxe;
    public static IResearchCrafting diamondShovel;
    public static IResearchCrafting diamondSword;

    public KnowledgeEfficiency(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_TECHNOLOGY);

        // Events related to efficiency of organisms and resources
        processing = new ResearchEvent("processing", getName(), 10, EventHelper.knowledge(com.ollieread.technomagi.common.init.Knowledge.processing), 1, 1, true, new String[] {});
        metabolism = new ResearchEvent("metabolism", getName(), 10, EventHelper.knowledge(com.ollieread.technomagi.common.init.Knowledge.metabolism), 1, 1, true, new String[] {});

        // Using up the wooden and stone tools
        woodPickaxe = new ResearchEvent("woodPickaxe", getName(), 1, EventHelper.itemBroke(ItemHelper.item("wooden_pickaxe")), 10, 1, new String[] {});
        woodAxe = new ResearchEvent("woodAxe", getName(), 1, EventHelper.itemBroke(ItemHelper.item("wooden_axe")), 10, 1, new String[] {});
        stonePickaxe = new ResearchEvent("stonePickaxe", getName(), 1, EventHelper.itemBroke(ItemHelper.item("stone_pickaxe")), 10, 1, new String[] { woodPickaxe.getName() });
        stoneAxe = new ResearchEvent("stoneAxe", getName(), 1, EventHelper.itemBroke(ItemHelper.item("stone_axe")), 10, 1, new String[] { woodAxe.getName() });

        // Crafting iron tools
        ironPickaxe = new ResearchCrafting("ironPickaxe", getName(), 5, ItemHelper.item("iron_pickaxe"), 1, 1, new String[] { stonePickaxe.getName() });
        ironAxe = new ResearchCrafting("ironAxe", getName(), 5, ItemHelper.item("iron_axe"), 1, 1, new String[] { stoneAxe.getName() });
        ironShovel = new ResearchCrafting("ironShovel", getName(), 5, ItemHelper.item("iron_shovel"), 1, 1, new String[] {});
        ironSword = new ResearchCrafting("ironSword", getName(), 5, ItemHelper.item("iron_sword"), 1, 1, new String[] {});

        // Crafting diamond tools
        diamondPickaxe = new ResearchCrafting("diamondPickaxe", getName(), 5, ItemHelper.item("diamond_pickaxe"), 1, 1, new String[] { ironPickaxe.getName() });
        diamondAxe = new ResearchCrafting("diamondAxe", getName(), 5, ItemHelper.item("diamond_axe"), 1, 1, new String[] { ironAxe.getName() });
        diamondShovel = new ResearchCrafting("diamondShovel", getName(), 5, ItemHelper.item("diamond_shovel"), 1, 1, new String[] { ironShovel.getName() });
        diamondSword = new ResearchCrafting("diamondSword", getName(), 5, ItemHelper.item("diamond_sword"), 1, 1, new String[] { ironSword.getName() });

    }
}