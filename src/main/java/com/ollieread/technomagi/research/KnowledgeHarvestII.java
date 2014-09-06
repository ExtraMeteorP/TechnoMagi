package com.ollieread.technomagi.research;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.crafting.ResearchCraftingIronAxe;
import com.ollieread.technomagi.research.crafting.ResearchCraftingIronPickaxe;
import com.ollieread.technomagi.research.crafting.ResearchCraftingIronShovel;
import com.ollieread.technomagi.research.mining.ResearchMiningHarvestDiamondOre;

public class KnowledgeHarvestII extends Knowledge
{

    public static IResearchCrafting craftingIronPickaxe;
    public static IResearchCrafting craftingIronAxe;
    public static IResearchCrafting craftingIronShovel;
    public static IResearchMining miningDiamondOre;

    public KnowledgeHarvestII(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        craftingIronPickaxe = new ResearchCraftingIronPickaxe("harvestIronPickaxe", getName(), 25);
        craftingIronAxe = new ResearchCraftingIronAxe("harvestIronAxe", getName(), 25);
        craftingIronShovel = new ResearchCraftingIronShovel("harvestIronShovel", getName(), 25);
        miningDiamondOre = new ResearchMiningHarvestDiamondOre("harvestDiamondOre", getName(), 25);
    }
}
