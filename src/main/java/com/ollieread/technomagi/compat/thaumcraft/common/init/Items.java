package com.ollieread.technomagi.compat.thaumcraft.common.init;

import net.minecraft.item.Item;

import com.ollieread.technomagi.compat.thaumcraft.common.item.ItemEssentiaContainer;

import cpw.mods.fml.common.registry.GameRegistry;

public class Items
{

    public static Item essentiaContainer;

    public static void init()
    {
        essentiaContainer = new ItemEssentiaContainer("essentia_container");

        GameRegistry.registerItem(essentiaContainer, "essentia_container");
    }

}
