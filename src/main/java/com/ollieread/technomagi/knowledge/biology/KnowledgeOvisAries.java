/**
 * 
 */
package com.ollieread.technomagi.knowledge.biology;

import net.minecraft.entity.passive.EntitySheep;

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
public class KnowledgeOvisAries extends Knowledge
{

    public static IResearch attacked;
    public static IResearch killed;
    public static IResearch sheared;
    public static IResearch analyseWoolWhite;
    public static IResearch analyseWoolLightGray;
    public static IResearch analyseWoolDarkGray;
    public static IResearch analyseWoolBlack;
    public static IResearch analyseWoolBrown;
    public static IResearch analyseWoolPink;

    public KnowledgeOvisAries(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);

        attacked = new ResearchEvent("attacked", getName(), EventHelper.entityAttacked(EntitySheep.class)).setProgress(5).setChance(5).setRepeatition(3).register();
        killed = new ResearchEvent("killed", getName(), EventHelper.entityKilled(EntitySheep.class)).setProgress(15).setChance(6).register();
        sheared = new ResearchEvent("sheared", getName(), "shearedSheep").setProgress(4).setChance(6).setRepeatition(5).register();
        analyseWoolWhite = new ResearchAnalysis("analyseWoolWhite", getName(), new ShapelessAnalysisRecipe(ItemHelper.block("wool", 1, 0))).setProgress(10).setChance(6).register();
        analyseWoolLightGray = new ResearchAnalysis("analyseWoolLightGray", getName(), new ShapelessAnalysisRecipe(ItemHelper.block("wool", 1, 8))).setProgress(10).setChance(6).register();
        analyseWoolDarkGray = new ResearchAnalysis("analyseWoolDarkGray", getName(), new ShapelessAnalysisRecipe(ItemHelper.block("wool", 1, 7))).setProgress(15).setChance(6).register();
        analyseWoolBlack = new ResearchAnalysis("analyseWoolBlack", getName(), new ShapelessAnalysisRecipe(ItemHelper.block("wool", 1, 15))).setProgress(15).setChance(6).register();
        analyseWoolBrown = new ResearchAnalysis("analyseWoolBrown", getName(), new ShapelessAnalysisRecipe(ItemHelper.block("wool", 1, 12))).setProgress(30).setChance(6).register();
        analyseWoolPink = new ResearchAnalysis("analyseWoolPink", getName(), new ShapelessAnalysisRecipe(ItemHelper.block("wool", 1, 6))).setProgress(40).setChance(6).register();
    }

    @Override
    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
