package com.ollieread.technomagi.common.item.staff;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import cofh.api.item.IToolHammer;

import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.util.ItemNBTHelper;
import com.ollieread.technomagi.util.PlayerHelper;

public class ItemStaffEngineer extends ItemStaffAbility implements IToolHammer
{

    public ItemStaffEngineer(String name)
    {
        super(name);
    }

    @Override
    public boolean isUsable(ItemStack stack, EntityLivingBase player, int x, int y, int z)
    {
        return PlayerHelper.isSpecialisation((EntityPlayer) player, Specialisations.engineer) && ((IToolHammer) Items.digitalTool).isUsable(stack, player, x, y, z);
    }

    @Override
    public void toolUsed(ItemStack stack, EntityLivingBase player, int x, int y, int z)
    {
        ((IToolHammer) Items.digitalTool).toolUsed(stack, player, x, y, z);
    }

    public void setFocus(ItemStack stack, int x, int y, int z)
    {
        ItemNBTHelper.setIntArray(stack, "Focus", new int[] { x, y, z });
    }

    public int[] getFocus(ItemStack stack)
    {
        return ItemNBTHelper.getIntArray(stack, "Focus");
    }

    public void resetFocus(ItemStack stack)
    {
        ItemNBTHelper.removeKey(stack, "Focus");
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.epic;
    }

    public boolean hasFocus(ItemStack stack)
    {
        return getFocus(stack).length == 3;
    }

}
