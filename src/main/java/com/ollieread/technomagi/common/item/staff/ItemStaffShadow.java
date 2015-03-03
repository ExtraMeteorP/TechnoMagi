package com.ollieread.technomagi.common.item.staff;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.util.PlayerHelper;

public class ItemStaffShadow extends ItemStaffAbility
{

    public ItemStaffShadow(String name)
    {
        super(name);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) attacker;
            PlayerTechnomagi technomage = PlayerTechnomagi.get(player);

            if (PlayerHelper.isSpecialisation(player, Specialisations.shadow)) {
                target.addPotionEffect(new PotionEffect(Potion.weakness.id, 100, 2));
            }
        }

        return true;
    }

}
