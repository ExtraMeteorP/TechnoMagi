package com.ollieread.technomagi.item;

import net.minecraft.item.Item;

import com.ollieread.technomagi.TechnoMagi;

public abstract class ItemTM extends Item
{

    public ItemTM()
    {
    }

    public ItemTM(String name)
    {
        setUnlocalizedName(name);
        setTextureName(name);
        setCreativeTab(TechnoMagi.tabTM);
    }

}
