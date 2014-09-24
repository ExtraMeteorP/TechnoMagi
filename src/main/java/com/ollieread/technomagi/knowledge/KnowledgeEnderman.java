package com.ollieread.technomagi.knowledge;

import java.util.Arrays;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchAnalysis;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeEnderman extends Knowledge
{

    public static IResearchEvent attacked;
    public static IResearchEvent attack;
    public static IResearchEvent kill;
    public static IResearchAnalysis enderpearl;
    public static IResearchEvent killed;

    public KnowledgeEnderman(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        attacked = new ResearchEvent("attackingEnderman", getName(), 25, "attackedByEnderman", false, 2, null);
        attack = new ResearchEvent("attackedEnderman", getName(), 25, "attackedEnderman", false, 2, null);
        kill = new ResearchEvent("killedEnderman", getName(), 25, "killedEnderman", false, 2, null);
        enderpearl = new ResearchAnalysis("analyseEnderpearlI", getName(), 25, Arrays.asList(new ItemStack[] { new ItemStack(Items.ender_pearl) }), false, 2, null);
        killed = new ResearchEvent("killedByEnderman", getName(), 50, "killedByEnderman", false, 1, null);
    }

}
