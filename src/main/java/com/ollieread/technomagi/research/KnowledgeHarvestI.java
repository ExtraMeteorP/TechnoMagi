package com.ollieread.technomagi.research;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.crafting.ResearchCraftingStoneAxe;
import com.ollieread.technomagi.research.crafting.ResearchCraftingStonePickaxe;
import com.ollieread.technomagi.research.crafting.ResearchCraftingStoneShovel;
import com.ollieread.technomagi.research.mining.ResearchMiningHarvestIronOre;

public class KnowledgeHarvestI extends Knowledge
{

    public static IResearchCrafting craftingStonePickaxe;
    public static IResearchCrafting craftingStoneAxe;
    public static IResearchCrafting craftingStoneShovel;
    public static IResearchMining miningIronOre;

    public KnowledgeHarvestI(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        craftingStonePickaxe = new ResearchCraftingStonePickaxe("harvestStonePickaxe", getName(), 25);
        craftingStoneAxe = new ResearchCraftingStoneAxe("harvestStoneAxe", getName(), 25);
        craftingStoneShovel = new ResearchCraftingStoneShovel("harvestStoneShovel", getName(), 25);
        miningIronOre = new ResearchMiningHarvestIronOre("harvestIronOre", getName(), 25);
    }
}
