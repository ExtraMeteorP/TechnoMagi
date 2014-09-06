package com.ollieread.technomagi.research.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.Research;
import com.ollieread.technomagi.common.Reference;

public class ResearchCraftingStoneAxe extends Research implements IResearchCrafting
{

    public ResearchCraftingStoneAxe(String name, String knowledge, int progress)
    {
        super(name, knowledge, progress, Reference.MODID.toLowerCase());
    }

    @Override
    public boolean isRepeating()
    {
        return false;
    }

    @Override
    public boolean canPerform(ExtendedPlayerKnowledge charon)
    {
        return true;
    }

    @Override
    public ItemStack getCrafting()
    {
        return new ItemStack(Items.stone_axe);
    }

}
