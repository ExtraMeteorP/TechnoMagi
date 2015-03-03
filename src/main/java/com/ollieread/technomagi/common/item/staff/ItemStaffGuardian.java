package com.ollieread.technomagi.common.item.staff;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;

import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.util.PlayerHelper;

public class ItemStaffGuardian extends ItemStaffAbility
{

    public ItemStaffGuardian(String name)
    {
        super(name);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) attacker;

            if (PlayerHelper.isSpecialisation(player, Specialisations.guardian)) {
                target.attackEntityFrom(new EntityDamageSource("generic", player), 2.0F);
            }
        }

        return true;
    }

}
