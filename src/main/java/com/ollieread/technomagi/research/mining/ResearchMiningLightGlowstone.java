package com.ollieread.technomagi.research.mining;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.api.research.IResearchMining;
import com.ollieread.technomagi.api.research.Research;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

public class ResearchMiningLightGlowstone extends Research implements IResearchMining
{

    public ResearchMiningLightGlowstone(String name, String knowledge, int progress)
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
    public ItemStack getMining()
    {
        return new ItemStack(Blocks.glowstone);
    }

}
