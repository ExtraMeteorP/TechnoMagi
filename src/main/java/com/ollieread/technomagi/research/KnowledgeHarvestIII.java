package com.ollieread.technomagi.research;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.crafting.ResearchCraftingDiamondAxe;
import com.ollieread.technomagi.research.crafting.ResearchCraftingDiamondPickaxe;
import com.ollieread.technomagi.research.crafting.ResearchCraftingDiamondShovel;
import com.ollieread.technomagi.research.mining.ResearchMiningHarvestObsidian;

public class KnowledgeHarvestIII extends Knowledge
{

    public static IResearchCrafting craftingDiamondPickaxe;
    public static IResearchCrafting craftingDiamondAxe;
    public static IResearchCrafting craftingDiamondShovel;
    public static IResearchMining miningObsidian;

    public KnowledgeHarvestIII(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        craftingDiamondPickaxe = new ResearchCraftingDiamondPickaxe("harvestDiamondPickaxe", getName(), 25);
        craftingDiamondAxe = new ResearchCraftingDiamondAxe("harvestDiamondAxe", getName(), 25);
        craftingDiamondShovel = new ResearchCraftingDiamondShovel("harvestDiamondShovel", getName(), 25);
        miningObsidian = new ResearchMiningHarvestObsidian("harvestObsidian", getName(), 25);
    }
}
