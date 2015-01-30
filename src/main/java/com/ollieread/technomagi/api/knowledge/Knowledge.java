package com.ollieread.technomagi.api.knowledge;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.TechnoMagiApi;

public class Knowledge
{

    protected String name;
    protected ResourceLocation icon;
    protected String category;
    protected List<String> prerequisites;
    protected int tier = 0;

    public Knowledge(String name, ResourceLocation icon, String category)
    {
        this.name = name;
        this.icon = icon;
        this.category = category;
        this.prerequisites = new ArrayList<String>();
    }

    public String getName()
    {
        return this.name;
    }

    public String getUnlocalisedName()
    {
        return TechnoMagiApi.PREFIX_KNOWLEDGE + this.name;
    }

    public ResourceLocation getIcon()
    {
        return this.icon;
    }

    public String getCategory()
    {
        return this.category;
    }

    public List<String> getPrerequisites()
    {
        return this.prerequisites;
    }

    public Knowledge addPrerequisite(String knowledge)
    {
        if (!prerequisites.contains(knowledge)) {
            prerequisites.add(knowledge);
        }

        return this;
    }

    public Knowledge setPrerequisites(List<String> prerequisites)
    {
        this.prerequisites = prerequisites;

        return this;
    }

    public int getTier()
    {
        return this.tier;
    }

    public void setTier(int tier)
    {
        this.tier = tier;
    }

}
