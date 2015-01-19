/**
 * 
 */
package com.ollieread.technomagi.knowledge.biology;

import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;
import com.ollieread.technomagi.util.ItemHelper;

/**
 * Metabolism Knowledge
 * 
 * Possible 160 points of data available. Any 10 of 16 required.
 * 
 * @author ollieread
 *
 */
public class KnowledgeMetabolism extends Knowledge
{

    public static IResearch eatPorkchop;
    public static IResearch eatBeef;
    public static IResearch eatChicken;
    public static IResearch eatFish;
    public static IResearch eatApple;
    public static IResearch eatPotato;
    public static IResearch eatCarrot;
    public static IResearch eatMelon;

    public static IResearch eatCookedPorkchop;
    public static IResearch eatCookedBeef;
    public static IResearch eatCookedChicken;
    public static IResearch eatCookedFish;
    public static IResearch eatCookedPotato;
    public static IResearch eatMushroomStew;
    public static IResearch eatPumpkinPie;
    public static IResearch eatBread;

    public KnowledgeMetabolism(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);

        eatPorkchop = new ResearchEvent("eatPorkchop", getName(), EventHelper.itemUse(ItemHelper.item("porkchop"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatBeef = new ResearchEvent("eatBeef", getName(), EventHelper.itemUse(ItemHelper.item("beef"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatChicken = new ResearchEvent("eatChicken", getName(), EventHelper.itemUse(ItemHelper.item("chicken"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatFish = new ResearchEvent("eatFish", getName(), EventHelper.itemUse(ItemHelper.item("fish"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatApple = new ResearchEvent("eatApple", getName(), EventHelper.itemUse(ItemHelper.item("apple"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatPotato = new ResearchEvent("eatPotato", getName(), EventHelper.itemUse(ItemHelper.item("potato"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatCarrot = new ResearchEvent("eatCarrot", getName(), EventHelper.itemUse(ItemHelper.item("carrot"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatMelon = new ResearchEvent("eatMelon", getName(), EventHelper.itemUse(ItemHelper.item("melon"))).setProgress(2).setChance(8).setRepeatition(5).register();

        eatCookedPorkchop = new ResearchEvent("eatCookedPorkchop", getName(), EventHelper.itemUse(ItemHelper.item("cooked_porkchop"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatCookedBeef = new ResearchEvent("eatCookedBeef", getName(), EventHelper.itemUse(ItemHelper.item("cooked_beef"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatCookedChicken = new ResearchEvent("eatCookedChicken", getName(), EventHelper.itemUse(ItemHelper.item("cooked_chicken"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatCookedFish = new ResearchEvent("eatCookedFish", getName(), EventHelper.itemUse(ItemHelper.item("cooked_fished"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatCookedPotato = new ResearchEvent("eatCookedPotato", getName(), EventHelper.itemUse(ItemHelper.item("baked_potato"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatMushroomStew = new ResearchEvent("eatMushroomStew", getName(), EventHelper.itemUse(ItemHelper.item("mushroom_stew"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatPumpkinPie = new ResearchEvent("eatPumpkinPie", getName(), EventHelper.itemUse(ItemHelper.item("pumpkin_pie"))).setProgress(2).setChance(8).setRepeatition(5).register();
        eatBread = new ResearchEvent("eatBread", getName(), EventHelper.itemUse(ItemHelper.item("bread"))).setProgress(2).setChance(8).setRepeatition(5).register();
    }

    @Override
    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
