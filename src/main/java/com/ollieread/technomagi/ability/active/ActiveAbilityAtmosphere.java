package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityCast;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseTarget;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.item.IStaff;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

public class ActiveAbilityAtmosphere extends AbilityActive
{
    protected Map<String, Integer> enhancements;
    protected int cost;
    protected int duration;

    public ActiveAbilityAtmosphere(String name)
    {
        super(name, Reference.MODID.toLowerCase());
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("life", 1);
        this.enhancements.put("force", 1);
        this.cost = Config.atmosphereCost;
        this.duration = Config.atmosphereDuration;
    }

    @Override
    public int getMaxFocus()
    {
        return 0;
    }

    @Override
    public boolean canIntervalFocus()
    {
        return false;
    }

    @Override
    public Map<String, Integer> getEnhancements()
    {
        return enhancements;
    }

    @Override
    public Map<String, Integer> getEnhancements(int mode)
    {
        return getEnhancements();
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, AbilityCast cast)
    {
        return charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityCast cast, ItemStack staff)
    {
        Random rand = new Random();
        int level = ((IStaff) staff.getItem()).getEnhancement(staff, "life");

        if (!cast.target.equals(AbilityUseTarget.ENTITY_LIVING) && !cast.target.equals(AbilityUseTarget.PLAYER)) {

            if (!charon.player.isPotionActive(Potion.waterBreathing)) {
                if (decreaseNanites(charon, cost)) {
                    if (!charon.player.worldObj.isRemote) {
                        charon.player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, duration * level, level));
                    }

                    return true;
                }
            }
        } else {
            if (charon.isSpecialisation("medic")) {
                EntityLivingBase entity = (EntityLivingBase) cast.targetEntity;

                if (entity != null && !entity.isPotionActive(Potion.waterBreathing)) {
                    if (decreaseNanites(charon, cost)) {
                        if (!charon.player.worldObj.isRemote) {
                            charon.player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, duration * level, level));
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

}
