package com.ollieread.technomagi.research;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.crafting.ResearchCraftingFurnace;
import com.ollieread.technomagi.research.crafting.ResearchSmeltingBrick;
import com.ollieread.technomagi.research.crafting.ResearchSmeltingCharcoal;
import com.ollieread.technomagi.research.crafting.ResearchSmeltingGlass;
import com.ollieread.technomagi.research.crafting.ResearchSmeltingGoldIngot;
import com.ollieread.technomagi.research.crafting.ResearchSmeltingIronIngot;
import com.ollieread.technomagi.research.crafting.ResearchSmeltingStone;

public class KnowledgeEndothermic extends Knowledge
{

    public static IResearchCrafting smeltingIronIngot;
    public static IResearchCrafting smeltingGoldIngot;
    public static IResearchCrafting smeltingGlass;
    public static IResearchCrafting smeltingStone;
    public static IResearchCrafting smeltingBrick;
    public static IResearchCrafting craftingFurnace;
    public static IResearchCrafting smeltingCharcoal;

    public KnowledgeEndothermic(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        craftingFurnace = new ResearchCraftingFurnace("craftingFurnace", this.getName(), 10);
        smeltingIronIngot = new ResearchSmeltingIronIngot("smeltingIronIngot", this.getName(), 15);
        smeltingGoldIngot = new ResearchSmeltingGoldIngot("smeltingGoldIngot", this.getName(), 15);
        smeltingGlass = new ResearchSmeltingGlass("smeltingGlass", this.getName(), 15);
        smeltingStone = new ResearchSmeltingStone("smeltingStone", this.getName(), 15);
        smeltingBrick = new ResearchSmeltingBrick("smeltingBrick", this.getName(), 15);
        smeltingCharcoal = new ResearchSmeltingCharcoal("smeltingCharcoal", this.getName(), 15);
    }
}
