package com.ollieread.technomagi.research;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.Research;
import com.ollieread.technomagi.common.Reference;

public class ResearchCrafting extends Research implements IResearchCrafting
{

    protected ItemStack crafting;

    public ResearchCrafting(String name, String knowledge, ItemStack crafting)
    {
        super(name, knowledge, Reference.MODID.toLowerCase());

        this.crafting = crafting;
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
