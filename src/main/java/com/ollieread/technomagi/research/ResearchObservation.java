package com.ollieread.technomagi.research;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchObservation;
import com.ollieread.ennds.research.Research;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.Reference;

public class ResearchObservation extends Research implements IResearchObservation
{

    protected int repeatition;
    protected String[] requirements;
    protected int chance;
    protected Class entity;
    protected ItemStack stack;
    protected int health;

    public ResearchObservation(String name, Class entity, String knowledge, int progress, ItemStack stack, int repeatition, int chance, int health, String[] requirements)
    {
        this(name, entity, knowledge, Reference.MODID.toLowerCase(), progress, stack, repeatition, chance, health, requirements);
    }

    public ResearchObservation(String name, Class entity, String knowledge, String modid, int progress, ItemStack stack, int repeatition, int chance, int health, String[] requirements)
    {
        super(name, knowledge, progress, Reference.MODID.toLowerCase(), requirements);

        this.stack = stack;
        this.repeatition = repeatition;
        this.chance = chance;
        this.health = health;
        this.entity = entity;

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
    public int getChance()
    {
        return chance;
    }

    @Override
    public Class getObservationEntity()
    {
        return entity;
    }

    @Override
    public ItemStack getObservationItemStack()
    {
        return stack;
    }

    @Override
    public int getModifiedHealth()
    {
        return health;
    }

}
