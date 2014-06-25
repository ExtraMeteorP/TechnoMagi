package com.ollieread.technomagi.research.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.api.research.IResearchCrafting;
import com.ollieread.technomagi.api.research.Research;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

public class ResearchCraftingLightTorch extends Research implements IResearchCrafting
{

    public ResearchCraftingLightTorch(String name, String knowledge, int progress)
    {
        super(name, knowledge, progress);
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
        return new ItemStack(Blocks.torch);
    }

}
