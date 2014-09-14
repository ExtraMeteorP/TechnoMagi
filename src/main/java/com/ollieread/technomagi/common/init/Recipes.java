package com.ollieread.technomagi.common.init;

import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.item.ItemStaff;

public class Recipes
{

    public static void init()
    {
        ItemStack basicStaff = ItemStaff.resetNBT(new ItemStack(Items.itemTechnomageStaff, 1, 0));
        ItemStack stackSampleVile = ItemStaff.resetNBT(new ItemStack(Items.itemSampleVile, 8, 0));
        ItemStack stackSampleExtractor = ItemStaff.resetNBT(new ItemStack(Items.itemSampleExtractor, 1));
        ItemStack stackNaniteContainer = ItemStaff.resetNBT(new ItemStack(Items.itemNaniteContainer, 1));

        ItemStack ironIngot = new ItemStack(net.minecraft.init.Items.iron_ingot, 1);
        ItemStack goldIngot = new ItemStack(net.minecraft.init.Items.gold_ingot, 1);
        ItemStack diamond = new ItemStack(net.minecraft.init.Items.diamond, 1);

    }

}
