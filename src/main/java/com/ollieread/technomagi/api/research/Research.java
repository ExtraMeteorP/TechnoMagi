package com.ollieread.technomagi.api.research;

import com.ollieread.technomagi.player.PlayerKnowledge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

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
    }

    public Research(String name, ResourceLocation icon, String knowledge, int progress)
    {
        researchName = name;
        researchIcon = icon;
        researchKnowledge = knowledge;
        researchProgress = progress;
    }

    @Override
    public String getName()
    {
        return researchName;
    }

    @Override
    public ResourceLocation getIcon()
    {
        return researchIcon;
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

}
