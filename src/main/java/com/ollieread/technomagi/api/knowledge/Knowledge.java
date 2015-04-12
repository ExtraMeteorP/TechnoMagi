package com.ollieread.technomagi.api.knowledge;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.TechnomagiApi;

public class Knowledge
{

    protected String name;
    protected ResourceLocation icon;
    protected String category;
    protected String prerequisite;
    protected int tier = 0;
    public int x;
    public int y;

    public Knowledge(String name, ResourceLocation icon, String category)
    {
        this.name = name;
        this.icon = icon;
        this.category = category;
    }

    public String getName()
    {
        return this.name;
    }

    public String getUnlocalisedName()
    {
        return TechnomagiApi.PREFIX_KNOWLEDGE + this.name;
    }

    public ResourceLocation getIcon()
    {
        return this.icon;
    }

    public String getCategory()
    {
        return this.category;
    }

    public String getPrerequisite()
    {
        return this.prerequisite;
    }

    public Knowledge addPrerequisite(String knowledge)
    {
        prerequisite = knowledge;

        return this;
    }

    public int getTier()
    {
        return this.tier;
    }

    public Knowledge setTier(int tier)
    {
        this.tier = tier;

        return this;
    }

    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void recipes()
    {

    }

}
