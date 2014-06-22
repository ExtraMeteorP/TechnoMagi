package com.ollieread.technomagi.api;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

    @SideOnly(Side.CLIENT)
    @Override
    public ResourceLocation getIcon()
    {
        return specialisationIcon;
    }

    public String getLocalisedName()
    {
        return I18n.format("specialisation." + this.getName());
    }

}
