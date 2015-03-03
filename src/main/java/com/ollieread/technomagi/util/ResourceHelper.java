package com.ollieread.technomagi.util;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.Technomagi;

public class ResourceHelper
{

    public static ResourceLocation texture(String path)
    {
        return new ResourceLocation(Technomagi.MODID.toLowerCase(), "textures/" + path);
    }

}
