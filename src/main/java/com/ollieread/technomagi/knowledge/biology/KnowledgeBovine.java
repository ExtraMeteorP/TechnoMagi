/**
 * 
 */
package com.ollieread.technomagi.knowledge.biology;

import net.minecraft.entity.passive.EntityCow;

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
public class KnowledgeBovine extends Knowledge
{

    public static IResearch attacked;
    public static IResearch killed;
    public static IResearch milked;
    public static IResearch analyseBeef;
    public static IResearch analyseLeather;
    public static IResearch analyseMilk;

    public KnowledgeBovine(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);

        attacked = new ResearchEvent("attacked", getName(), EventHelper.entityAttacked(EntityCow.class)).setProgress(5).setChance(5).setRepeatition(3).register();
        killed = new ResearchEvent("killed", getName(), EventHelper.entityKilled(EntityCow.class)).setProgress(15).setChance(6).register();
        milked = new ResearchEvent("milked", getName(), "milkedCow").setProgress(4).setChance(6).setRepeatition(5).register();
        analyseBeef = new ResearchAnalysis("analyseBeef", getName(), new ShapelessAnalysisRecipe(ItemHelper.item("beef"))).setProgress(15).setChance(6).register();
        analyseLeather = new ResearchAnalysis("analyseLeather", getName(), new ShapelessAnalysisRecipe(ItemHelper.item("leather"))).setProgress(15).setChance(6).register();
        analyseMilk = new ResearchAnalysis("analyseMilk", getName(), new ShapelessAnalysisRecipe(ItemHelper.item("milk_bucket"))).setProgress(20).setChance(6).register();
    }

    @Override
    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
