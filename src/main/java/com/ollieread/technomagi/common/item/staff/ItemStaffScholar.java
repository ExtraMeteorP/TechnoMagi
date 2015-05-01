package com.ollieread.technomagi.common.item.staff;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemStaffScholar extends ItemStaffAbility
{

    public ItemStaffScholar(String name)
    {
        super(name);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.epic;
    }

}
