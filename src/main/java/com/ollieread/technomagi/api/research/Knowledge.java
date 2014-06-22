package com.ollieread.technomagi.api.research;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Knowledge implements IKnowledge
{

    protected String knowledgeName;
    protected ResourceLocation knowledgeIcon;

    public Knowledge(String name)
    {
        knowledgeName = name;
        knowledgeIcon = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/research/" + name + ".png");

        TMRegistry.registerKnowledge(this);
    }

    public Knowledge(String name, ResourceLocation icon)
    {
        knowledgeName = name;
        knowledgeIcon = icon;

        TMRegistry.registerKnowledge(this);
    }

    @Override
    public String getName()
    {
        return knowledgeName;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ResourceLocation getIcon()
    {
        return knowledgeIcon;
    }

    public String getLocalisedName()
    {
        return I18n.format("knowledge." + this.getName());
    }

}
