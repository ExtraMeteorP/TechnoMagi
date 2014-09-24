package com.ollieread.technomagi.knowledge;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchCrafting;

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

        craftingFurnace = new ResearchCrafting("craftingFurnace", this.getName(), 10, new ItemStack(Blocks.furnace), false, 2, null);
        smeltingIronIngot = new ResearchCrafting("smeltingIronIngot", this.getName(), 15, new ItemStack(Items.iron_ingot), false, 2, null);
        smeltingGoldIngot = new ResearchCrafting("smeltingGoldIngot", this.getName(), 15, new ItemStack(Items.gold_ingot), false, 2, null);
        smeltingGlass = new ResearchCrafting("smeltingGlass", this.getName(), 15, new ItemStack(Blocks.glass), false, 2, null);
        smeltingStone = new ResearchCrafting("smeltingStone", this.getName(), 15, new ItemStack(Blocks.stone), false, 2, null);
        smeltingBrick = new ResearchCrafting("smeltingBrick", this.getName(), 15, new ItemStack(Items.brick), false, 2, null);
        smeltingCharcoal = new ResearchCrafting("smeltingCharcoal", this.getName(), 15, new ItemStack(Items.coal, 1, 1), false, 2, null);
    }
}
