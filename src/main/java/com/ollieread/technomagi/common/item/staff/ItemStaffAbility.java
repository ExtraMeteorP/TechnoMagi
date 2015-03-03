package com.ollieread.technomagi.common.item.staff;

import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.api.ability.IAbilityCast;
import com.ollieread.technomagi.api.ability.IAbilityItem;
import com.ollieread.technomagi.common.item.ItemBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStaffAbility extends ItemBase implements IAbilityItem
{

    public ItemStaffAbility(String name)
    {
        super(name);

        this.maxStackSize = 1;
    }

    @Override
    public boolean isAbilityLocked(ItemStack stack)
    {
        return false;
    }

    @Override
    public IAbilityCast getLockedAbility(ItemStack stack)
    {
        return null;
    }

    @Override
    public int getAbilityMode(ItemStack stack)
    {
        return 0;
    }

    @Override
    public boolean canCast(ItemStack stack)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.bow;
    }

}
