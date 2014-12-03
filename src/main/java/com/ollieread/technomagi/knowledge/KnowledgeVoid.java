package com.ollieread.technomagi.knowledge;

import java.util.Arrays;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.knowledge.research.ResearchAnalysis;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;
import com.ollieread.technomagi.knowledge.research.ResearchMining;

public class KnowledgeVoid extends Knowledge
{

    public static IResearchEvent damage;
    public static IResearchMining mineVoidstone;
    public static IResearchAnalysis analyseVoidstone;

    public KnowledgeVoid(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        damage = new ResearchEvent("damagedByVoid", getName(), 10, "damageVoid", true, 1, null);
        mineVoidstone = new ResearchMining("mineVoidstone", getName(), 50, new ItemStack(Blocks.blockVoidstone), false, 3, null);
        analyseVoidstone = new ResearchAnalysis("analyseVoidstone", getName(), 50, Arrays.asList(new ItemStack[] { new ItemStack(Blocks.blockVoidstone) }), false, 2, null);
    }
}
