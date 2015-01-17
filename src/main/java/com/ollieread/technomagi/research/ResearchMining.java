package com.ollieread.technomagi.research;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Research;
import com.ollieread.technomagi.common.Reference;

public class ResearchMining extends Research implements IResearchMining
{

    protected ItemStack mining;

    public ResearchMining(String name, String knowledge, ItemStack mining)
    {
        super(name, knowledge, Reference.MODID.toLowerCase());

        this.mining = mining;
    }

    @Override
    public boolean canPerform(ExtendedPlayerKnowledge charon)
    {
        return true;
    }

    @Override
    public ItemStack getMining()
    {
        return mining;
    }

}
