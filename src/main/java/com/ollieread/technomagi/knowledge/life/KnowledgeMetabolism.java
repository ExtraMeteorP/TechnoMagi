package com.ollieread.technomagi.knowledge.life;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchCrafting;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgeMetabolism extends Knowledge
{

    public static IResearchEvent eatPorkchop;
    public static IResearchEvent eatBeef;
    public static IResearchEvent eatChicken;
    public static IResearchEvent eatFish;

    public static IResearchCrafting cookPorkchop;
    public static IResearchCrafting cookBeef;
    public static IResearchCrafting cookChicken;
    public static IResearchCrafting cookFish;

    public static IResearchEvent eatCookedPorkchop;
    public static IResearchEvent eatCookedBeef;
    public static IResearchEvent eatCookedChicken;
    public static IResearchEvent eatCookedFish;

    public static IResearchCrafting makeBread;

    public static IResearchEvent eatApple;
    public static IResearchEvent eatCarrot;
    public static IResearchEvent eatBread;
    public static IResearchEvent eatPotato;

    public static IResearchCrafting cookPotato;

    public static IResearchEvent eatBakedPotato;

    public KnowledgeMetabolism(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_LIFE);

        eatPorkchop = new ResearchEvent("eatPorkchop", getName(), 5, EventHelper.item(ItemHelper.item("porkchop")), 1, 12, new String[] {});
        eatBeef = new ResearchEvent("eatBeef", getName(), 5, EventHelper.item(ItemHelper.item("beef")), 1, 12, new String[] {});
        eatChicken = new ResearchEvent("eatChicken", getName(), 5, EventHelper.item(ItemHelper.item("chicken")), 1, 12, new String[] {});
        eatFish = new ResearchEvent("eatFish", getName(), 5, EventHelper.item(ItemHelper.item("fish")), 1, 12, new String[] {});

        cookPorkchop = new ResearchCrafting("cookPorkchop", getName(), 6, ItemHelper.item("cooked_porkchop"), 1, 6, new String[] { eatPorkchop.getName() });
        cookBeef = new ResearchCrafting("cookBeef", getName(), 6, ItemHelper.item("cooked_beef"), 1, 6, new String[] { eatBeef.getName() });
        cookChicken = new ResearchCrafting("cookChicken", getName(), 6, ItemHelper.item("cooked_chicken"), 1, 6, new String[] { eatChicken.getName() });
        cookFish = new ResearchCrafting("cookFish", getName(), 6, ItemHelper.item("cooked_fished"), 1, 6, new String[] { eatFish.getName() });

        eatCookedPorkchop = new ResearchEvent("eatCookedPorkchop", getName(), 6, EventHelper.item(ItemHelper.item("cooked_porkchop")), 1, 12, new String[] {});
        eatCookedBeef = new ResearchEvent("eatCookedBeef", getName(), 6, EventHelper.item(ItemHelper.item("cooked_beef")), 1, 12, new String[] {});
        eatCookedChicken = new ResearchEvent("eatCookedChicken", getName(), 6, EventHelper.item(ItemHelper.item("cooked_chicken")), 1, 12, new String[] {});
        eatCookedFish = new ResearchEvent("eatCookedFish", getName(), 6, EventHelper.item(ItemHelper.item("cooked_fished")), 1, 12, new String[] {});

        makeBread = new ResearchCrafting("makeBread", getName(), 6, ItemHelper.item("bread"), 1, 12, new String[] {});

        eatApple = new ResearchEvent("eatApple", getName(), 5, EventHelper.item(ItemHelper.item("apple")), 1, 12, new String[] {});
        eatCarrot = new ResearchEvent("eatCarrot", getName(), 5, EventHelper.item(ItemHelper.item("carrot")), 1, 12, new String[] {});
        eatPotato = new ResearchEvent("eatPotato", getName(), 5, EventHelper.item(ItemHelper.item("potato")), 1, 12, new String[] {});
        eatBread = new ResearchEvent("eatBread", getName(), 5, EventHelper.item(ItemHelper.item("bread")), 1, 12, new String[] { makeBread.getName() });

        cookPotato = new ResearchCrafting("cookPotato", getName(), 6, ItemHelper.item("baked_potato"), 1, 12, new String[] { eatPotato.getName() });

        eatBakedPotato = new ResearchEvent("eatBakedPotato", getName(), 6, EventHelper.item(ItemHelper.item("baked_potato")), 1, 12, new String[] { cookPotato.getName() });
    }
}
