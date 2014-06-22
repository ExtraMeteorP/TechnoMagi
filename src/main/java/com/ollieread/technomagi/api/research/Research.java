package com.ollieread.technomagi.api.research;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.TMRegistry;

public abstract class Research implements IResearch
{

    protected String researchName;
    protected ResourceLocation researchIcon;
    protected String researchKnowledge;
    protected int researchProgress;

    public Research(String name, String knowledge, int progress)
    {
        researchName = name;
        researchIcon = new ResourceLocation("technomagi", "textures/research/" + name + ".png");
        researchKnowledge = knowledge;
        researchProgress = progress;

        TMRegistry.registerResearch(this);
    }

    public Research(String name, ResourceLocation icon, String knowledge, int progress)
    {
        researchName = name;
        researchIcon = icon;
        researchKnowledge = knowledge;
        researchProgress = progress;

        TMRegistry.registerResearch(this);
    }

    @Override
    public String getName()
    {
        return researchName;
    }

    @Override
    public String getKnowledge()
    {
        return researchKnowledge;
    }

    @Override
    public int getProgress()
    {
        return researchProgress;
    }

    public String getLocalisedName()
    {
        return I18n.format("research." + this.getName());
    }

}
