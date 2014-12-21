package com.ollieread.technomagi.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemComponent extends ItemTMSubtypes
{

    public ItemComponent(String name)
    {
        super(name, new String[] { "powerWater", "chargeableGeloid", "powerCell", "photovoltaicCell", "voidCell" });
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b)
    {
        list.add("Component");
    }

}
