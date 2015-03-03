package com.ollieread.technomagi.common.item.staff;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.common.item.ItemBase;
import com.ollieread.technomagi.util.PlayerHelper;

public class ItemStaffBasic extends ItemBase
{

    public ItemStaffBasic(String name)
    {
        super(name);

        this.maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (PlayerHelper.hasSpecialised(player)) {
            onItemRightClick(stack, world, player);
            return true;
        }

        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (PlayerHelper.hasSpecialised(player)) {
            if (Config.multiplayerMode) {
                if (PlayerHelper.isSpecialisation(player, Specialisations.cleric)) {
                    stack.stackSize--;
                    PlayerHelper.giveInventoryItem(player, new ItemStack(Items.staffCleric));
                } else if (PlayerHelper.isSpecialisation(player, Specialisations.engineer)) {
                    stack.stackSize--;
                    PlayerHelper.giveInventoryItem(player, new ItemStack(Items.staffEngineer));
                } else if (PlayerHelper.isSpecialisation(player, Specialisations.guardian)) {
                    stack.stackSize--;
                    PlayerHelper.giveInventoryItem(player, new ItemStack(Items.staffGuardian));
                } else if (PlayerHelper.isSpecialisation(player, Specialisations.scholar)) {
                    stack.stackSize--;
                    PlayerHelper.giveInventoryItem(player, new ItemStack(Items.staffScholar));
                } else if (PlayerHelper.isSpecialisation(player, Specialisations.shadow)) {
                    stack.stackSize--;
                    PlayerHelper.giveInventoryItem(player, new ItemStack(Items.staffShadow));
                }
            } else {
                stack.stackSize--;
                PlayerHelper.giveInventoryItem(player, new ItemStack(Items.staffScholar));
            }
        }

        return stack;
    }

}
