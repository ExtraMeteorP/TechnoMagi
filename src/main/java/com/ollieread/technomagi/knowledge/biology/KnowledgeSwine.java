/**
 * 
 */
package com.ollieread.technomagi.knowledge.biology;

import net.minecraft.entity.passive.EntityPig;

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
public class KnowledgeSwine extends Knowledge
{

    public static IResearch attacked;
    public static IResearch killed;
    public static IResearch analysePork;
    public static IResearch saddled;
    public static IResearch ridden;

    public KnowledgeSwine(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);

        attacked = new ResearchEvent("attacked", getName(), EventHelper.entityAttacked(EntityPig.class)).setProgress(5).setChance(5).setRepeatition(3).register();
        killed = new ResearchEvent("killed", getName(), EventHelper.entityKilled(EntityPig.class)).setProgress(15).setChance(6).register();
        saddled = new ResearchEvent("saddled", getName(), "saddledPig").setProgress(4).setChance(6).setRepeatition(5).register();
        analysePork = new ResearchAnalysis("analysePork", getName(), new ShapelessAnalysisRecipe(ItemHelper.item("porkchop"))).setProgress(15).setChance(6).register();
        ridden = new ResearchEvent("ridingPig", getName(), "ridingPig").setProgress(5).setChance(6).setRepeatition(7).register();
    }

    @Override
    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
