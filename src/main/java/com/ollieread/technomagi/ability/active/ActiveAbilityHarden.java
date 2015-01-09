package com.ollieread.technomagi.ability.active;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityCast;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.common.init.Potions;

public class ActiveAbilityHarden extends AbilityActive
{
    protected int cost;

    public ActiveAbilityHarden(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        cost = Config.hardenCost;
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
    public boolean canUse(ExtendedPlayerKnowledge charon, AbilityCast cast)
    {
        return charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityCast cast, ItemStack staff)
    {
        if (!charon.player.isPotionActive(Potions.potionHardness)) {
            if (decreaseNanites(charon, cost)) {
                if (!charon.player.worldObj.isRemote) {
                    Random rand = new Random();

                    charon.player.addPotionEffect(new PotionEffect(Potions.potionHardness.id, 400, 0, false));
                }

                return true;
            }
        }

        return false;
    }

}
