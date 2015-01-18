/**
 * 
 */
package com.ollieread.technomagi.knowledge.biology;

import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.ennds.research.ShapelessAnalysisRecipe;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.KnowledgeCategoryBiology;
import com.ollieread.technomagi.research.ResearchAnalysis;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.util.EventHelper;
import com.ollieread.technomagi.util.ItemHelper;

/**
 * @author ollieread
 *
 */
public class KnowledgeNeurology extends Knowledge
{

    public static IResearch aggressive;
    public static IResearch passive;
    public static IResearch perception;
    public static IResearch analysis;

    public KnowledgeNeurology(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);

        aggressive = new ResearchEvent("aggressive", getName(), EventHelper.knowledge(KnowledgeCategoryBiology.ethologyAggressive), true).setProgress(20);
        passive = new ResearchEvent("passive", getName(), EventHelper.knowledge(KnowledgeCategoryBiology.ethologyPassive), true).setProgress(20);
        perception = new ResearchEvent("perception", getName(), EventHelper.knowledge(KnowledgeCategoryBiology.perception), true).setProgress(20);

        analysis = new ResearchAnalysis("analyseBrain", getName(), new ShapelessAnalysisRecipe(ItemHelper.brain())).setProgress(5).setRepeatition(8);
    }

    @Override
    public String[] getIntrigue()
    {
        return Information.getInformation("intrigue", getName());
    }

}
