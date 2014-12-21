package com.ollieread.technomagi.research;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.Research;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.Reference;

public class ResearchCrafting extends Research implements IResearchCrafting
{

    protected int repeatition;
    protected int chance;
    protected ItemStack crafting;

    public ResearchCrafting(String name, String knowledge, int progress, ItemStack crafting, int repeatition, int chance, String[] requirements)
    {
        this(name, knowledge, progress, Reference.MODID.toLowerCase(), crafting, repeatition, chance, requirements);
    }

    public ResearchCrafting(String name, String knowledge, int progress, String Modid, ItemStack crafting, int repeatition, int chance, String[] requirements)
    {
        super(name, knowledge, progress, Modid, requirements);

        this.crafting = crafting;
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
    public ItemStack getCrafting()
    {
        return crafting;
    }

    @Override
    public boolean canPerform(ExtendedPlayerKnowledge charon)
    {
        return true;
    }

}
