package com.ollieread.technomagi.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.ollieread.technomagi.common.init.Blocks;

public class CreativeTabTM extends CreativeTabs
{

    public CreativeTabTM()
    {
        super("tabTm");
    }

    @Override
    public Item getTabIconItem()
    {
        return Item.getItemFromBlock(Blocks.blockArchive);
    }

}
