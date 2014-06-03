package com.ollieread.technomagi.knowledge;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.research.Research;
import com.ollieread.technomagi.player.PlayerKnowledge;

public class ResearchFireInFire extends Research
{

    public ResearchFireInFire(String name, String knowledge, int progress)
    {
        super(name, knowledge, progress);
    }

    @Override
    public int getEvent()
    {
        return TMRegistry.EVENT_IN_FIRE;
    }

    @Override
    public boolean isRepeating()
    {
        return false;
    }

    @Override
    public boolean canPerform(PlayerKnowledge charon)
    {
        return !charon.hasResearched(getName());
    }

}
