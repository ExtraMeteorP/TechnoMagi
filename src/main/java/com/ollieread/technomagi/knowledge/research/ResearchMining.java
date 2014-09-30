package com.ollieread.technomagi.knowledge.research;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Research;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.Reference;

public class ResearchMining extends Research implements IResearchMining
{

    protected boolean repeating;
    protected String[] requirements;
    protected int chance;
    protected ItemStack mining;

    public ResearchMining(String name, String knowledge, int progress, ItemStack mining, boolean repeating, int chance, String[] requirements)
    {
        this(name, knowledge, progress, Reference.MODID.toLowerCase(), mining, repeating, chance, requirements);
    }

    public ResearchMining(String name, String knowledge, int progress, String Modid, ItemStack mining, boolean repeating, int chance, String[] requirements)
    {
        super(name, knowledge, progress, Modid);

        this.mining = mining;
        this.repeating = repeating;
        this.chance = chance;
        this.requirements = requirements;

        ResearchRegistry.registerResearch(this);
    }

    @Override
    public boolean isRepeating()
    {
        return repeating;
    }

    @Override
    public int getChance()
    {
        return chance;
    }

    @Override
    public boolean canPerform(ExtendedPlayerKnowledge charon)
    {
        if (requirements != null && requirements.length > 0) {
            for (int i = 0; i < requirements.length; i++) {
                if (!charon.hasKnowledge(requirements[i])) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack getMining()
    {
        return null;
    }

}
