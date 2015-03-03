package com.ollieread.technomagi.api.specialisation;

import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.TechnomagiApi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Specialisation
{
    protected String name;
    protected ResourceLocation icon;

    public Specialisation(String name, ResourceLocation icon)
    {
        this.name = name;
        this.icon = icon;
    }

    public String getName()
    {
        return name;
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation getIcon()
    {
        return icon;
    }

    public String getUnlocalisedName()
    {
        return TechnomagiApi.PREFIX_SPECIALISATION + name;
    }

    public void applyAttributes(BaseAttributeMap map)
    {

    }

    public int modifyDamage(DamageSource damage, int amount)
    {
        return amount;
    }

}
