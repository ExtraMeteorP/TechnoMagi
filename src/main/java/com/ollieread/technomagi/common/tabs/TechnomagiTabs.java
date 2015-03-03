package com.ollieread.technomagi.common.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TechnomagiTabs
{

    public static CreativeTabs blocks = new CreativeTabs("tab.technomagi.blocks")
    {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return null;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getIconItemStack()
        {
            return new ItemStack(Blocks.resource, 1, 0);
        }
    };

    public static CreativeTabs items = new CreativeTabs("tab.technomagi.items")
    {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return null;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getIconItemStack()
        {
            return new ItemStack(Items.crystal, 1, 0);
        }
    };

    public static CreativeTabs entity = new CreativeTabs("tab.technomagi.entities")
    {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return null;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getIconItemStack()
        {
            return new ItemStack(Items.entityCapture);
        }
    };

}
