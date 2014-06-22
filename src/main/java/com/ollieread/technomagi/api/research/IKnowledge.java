package com.ollieread.technomagi.api.research;

import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IKnowledge
{

    public String getName();

    @SideOnly(Side.CLIENT)
    public ResourceLocation getIcon();

    public String getLocalisedName();

}
