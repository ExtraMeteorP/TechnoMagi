package com.ollieread.technomagi.api;

import com.ollieread.technomagi.common.Reference;

import net.minecraft.util.ResourceLocation;

public class Specialisation implements ISpecialisation
{

    public String specialisationName;
    public ResourceLocation specialisationIcon;

    public Specialisation(String name)
    {
        specialisationName = name;
        specialisationIcon = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/specialisations/" + name + ".png");
    }

    public Specialisation(String name, ResourceLocation icon)
    {
        specialisationName = name;
        specialisationIcon = icon;
    }

    @Override
    public String getName()
    {
        return specialisationName;
    }

    @Override
    public ResourceLocation getIcon()
    {
        return specialisationIcon;
    }

}
