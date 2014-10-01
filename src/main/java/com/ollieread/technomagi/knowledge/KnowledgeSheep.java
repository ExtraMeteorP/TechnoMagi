package com.ollieread.technomagi.knowledge;

import java.util.Arrays;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchAnalysis;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeSheep extends Knowledge
{

    public static IResearchEvent breed;
    public static IResearchEvent kill;
    public static IResearchEvent shear;
    public static IResearchAnalysis wool;

    public KnowledgeSheep(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        breed = new ResearchEvent("breedSheep", getName(), 25, "breedingSheep", false, 2, null);
        kill = new ResearchEvent("killSheep", getName(), 25, "killedSheep", false, 2, null);
        shear = new ResearchEvent("shearSheep", getName(), 25, "shearedSheep", false, 2, null);
        wool = new ResearchAnalysis("analyseWool", getName(), 25, Arrays.asList(new ItemStack[] { new ItemStack(net.minecraft.init.Blocks.wool, 1, 0) }), false, 2, null);
    }
}
