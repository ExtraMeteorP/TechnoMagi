package com.ollieread.technomagi.knowledge.research;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchObservation;
import com.ollieread.ennds.research.Research;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.Reference;

public class ResearchObservation extends Research implements IResearchObservation
{

    protected boolean repeating;
    protected String[] requirements;
    protected int chance;
    protected Class entity;
    protected ItemStack stack;
    protected int health;

    public ResearchObservation(String name, Class entity, String knowledge, int progress, ItemStack stack, boolean repeating, int chance, String[] requirements, int health)
    {
        this(name, entity, knowledge, Reference.MODID.toLowerCase(), progress, stack, repeating, chance, requirements, health);
    }

    public ResearchObservation(String name, Class entity, String knowledge, String modid, int progress, ItemStack stack, boolean repeating, int chance, String[] requirements, int health)
    {
        super(name, knowledge, progress, Reference.MODID.toLowerCase());

        this.stack = stack;
        this.repeating = repeating;
        this.chance = chance;
        this.requirements = requirements;
        this.health = health;
        this.entity = entity;

        ResearchRegistry.registerResearch(this);
    }

    @Override
    public boolean isRepeating()
    {
        return repeating;
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
