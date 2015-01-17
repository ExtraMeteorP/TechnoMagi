package com.ollieread.technomagi.research;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchObservation;
import com.ollieread.ennds.research.Research;
import com.ollieread.technomagi.common.Reference;

public class ResearchObservation extends Research implements IResearchObservation
{

    protected Class entity;
    protected ItemStack stack;
    protected int health;

    public ResearchObservation(String name, Class entity, String knowledge, ItemStack stack, int health)
    {
        super(name, knowledge, Reference.MODID.toLowerCase());

        this.entity = entity;
        this.health = health;
    }

    @Override
    public boolean canPerform(ExtendedPlayerKnowledge charon)
    {
        if (researchRequirements != null && researchRequirements.length > 0) {
            for (int i = 0; i < researchRequirements.length; i++) {
                if (!charon.hasKnowledge(researchRequirements[i])) {
                    return false;
                }
            }
        }

        return true;
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
