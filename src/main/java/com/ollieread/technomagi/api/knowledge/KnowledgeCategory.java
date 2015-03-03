package com.ollieread.technomagi.api.knowledge;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.TechnomagiApi;

public class KnowledgeCategory
{

    protected String name;
    protected ResourceLocation icon;
    public int x;
    public int y;

    public KnowledgeCategory(String name, ResourceLocation icon)
    {
        this.name = name;
        this.icon = icon;
    }

    public String getName()
    {
        return this.name;
    }

    public String getUnlocalisedName()
    {
        return TechnomagiApi.PREFIX_CATEGORY + this.name;
    }

    public ResourceLocation getIcon()
    {
        return this.icon;
    }

    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

}
