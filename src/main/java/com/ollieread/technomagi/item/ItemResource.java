package com.ollieread.technomagi.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemResource extends ItemTMSubtypes
{

    public ItemResource(String name)
    {
        super(name, new String[] { "etheriumCrystal", "reluxCrystal", "voidCrystal", "etheriumDust", "ironDust", "goldDust", "diamondDust", "mouldableDust", "etheriumIngot", "reluxIngot", "voidIngot", "mouldableIngot", "smartmetalIngot", "ironRod", "goldRod", "diamondRod", "ironSheet", "goldSheet", "diamondSheet", "smartmetalSheet" });
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b)
    {
        list.add("Resource");
    }

}
