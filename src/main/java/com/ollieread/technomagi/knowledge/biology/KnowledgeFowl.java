/**
 * 
 */
package com.ollieread.technomagi.knowledge.biology;

import net.minecraft.entity.passive.EntityChicken;

import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.ennds.research.ShapelessAnalysisRecipe;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchAnalysis;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;
import com.ollieread.technomagi.util.ItemHelper;

/**
 * @author ollieread
 *
 */
public class KnowledgeFowl extends Knowledge
{

    public static IResearch attacked;
    public static IResearch killed;
    public static IResearch egged;
    public static IResearch analyseChicken;
    public static IResearch analyseFeather;
    public static IResearch analyseEgg;

    public KnowledgeFowl(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);

        attacked = new ResearchEvent("attacked", getName(), EventHelper.entityAttacked(EntityChicken.class)).setProgress(5).setChance(5).setRepeatition(3);
        killed = new ResearchEvent("killed", getName(), EventHelper.entityKilled(EntityChicken.class)).setProgress(15).setChance(6);
        egged = new ResearchEvent("egged", getName(), EventHelper.itemUse(ItemHelper.item("egg"))).setProgress(4).setChance(6).setRepeatition(5);
        analyseChicken = new ResearchAnalysis("analyseChicken", getName(), new ShapelessAnalysisRecipe(ItemHelper.item("chicken"))).setProgress(15).setChance(6);
        analyseFeather = new ResearchAnalysis("analyseFeather", getName(), new ShapelessAnalysisRecipe(ItemHelper.item("feather"))).setProgress(15).setChance(6);
        analyseEgg = new ResearchAnalysis("analyseEgg", getName(), new ShapelessAnalysisRecipe(ItemHelper.item("egg"))).setProgress(20).setChance(6);
    }

    @Override
    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
