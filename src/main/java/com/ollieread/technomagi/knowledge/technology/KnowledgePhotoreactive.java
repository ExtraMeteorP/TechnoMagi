package com.ollieread.technomagi.knowledge.technology;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.ennds.research.ShapedAnalysisRecipe;
import com.ollieread.ennds.research.ShapelessAnalysisRecipe;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.ResearchAnalysis;
import com.ollieread.technomagi.research.ResearchMining;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgePhotoreactive extends Knowledge
{

    public IResearchMining mineQuartz;
    public IResearchAnalysis analyseQuartz;
    public IResearchAnalysis analyseSunflower;
    public IResearchAnalysis analyseDetector;
    public IResearchAnalysis analyseDetectorRecipe;

    public KnowledgePhotoreactive(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_TECHNOLOGY);

        mineQuartz = new ResearchMining("mineQuartz", getName(), 20, ItemHelper.block("quartz_ore"), 1, 3, new String[] {});
        analyseQuartz = new ResearchAnalysis("analyseQuartz", getName(), 20, new ShapelessAnalysisRecipe(ItemHelper.item("quartz")), 1, 5, new String[] { getName() + ".mineQuartz" });
        analyseSunflower = new ResearchAnalysis("analyseSunflower", getName(), 20, new ShapelessAnalysisRecipe(ItemHelper.block("double_plant", 1, 1)), 1, 6);
        analyseDetector = new ResearchAnalysis("analyseDetector", getName(), 20, new ShapelessAnalysisRecipe(ItemHelper.block("daylight_detector")), 1, 4);
        analyseDetectorRecipe = new ResearchAnalysis("analyseDetectorRecipe", getName(), 20, new ShapedAnalysisRecipe("xxx", "yyy", "zzz", 'x', ItemHelper.block("glass"), 'y', ItemHelper.item("quartz"), 'z', ItemHelper.block("wooden_slab")), 1, 4, new String[] { analyseDetector.getName() });
    }
}
