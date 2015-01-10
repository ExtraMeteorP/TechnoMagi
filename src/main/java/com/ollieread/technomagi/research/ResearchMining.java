package com.ollieread.technomagi.research;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Research;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.Reference;

public class ResearchMining extends Research implements IResearchMining
{

    protected int repeatition;
    protected int chance;
    protected ItemStack mining;

    public ResearchMining(String name, String knowledge, int progress, ItemStack mining, int repeatition, int chance, String[] requirements)
    {
        this(name, knowledge, progress, Reference.MODID.toLowerCase(), mining, repeatition, chance, requirements);
    }

    public ResearchMining(String name, String knowledge, int progress, String Modid, ItemStack mining, int repeatition, int chance, String[] requirements)
    {
        super(name, knowledge, progress, Modid, requirements);

        this.mining = mining;
        this.repeatition = repeatition;
        this.chance = chance;

        ResearchRegistry.registerResearch(this);
    }

    @Override
    public boolean isRepeating()
    {
        return repeatition > 1;
    }

    @Override
    public int getMaxRepeatition()
    {
        return repeatition;
    }

    @Override
    public int getChance()
    {
        return chance;
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
