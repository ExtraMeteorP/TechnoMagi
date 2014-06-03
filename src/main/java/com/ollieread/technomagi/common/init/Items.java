package com.ollieread.technomagi.common.init;

import com.ollieread.technomagi.item.ItemConstructModule;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class Items
{

    public static Item itemConstructModule;

    public static void init()
    {
        itemConstructModule = new ItemConstructModule("itemConstructModule");

        GameRegistry.registerItem(itemConstructModule, "itemConstructModule");
    }

}
