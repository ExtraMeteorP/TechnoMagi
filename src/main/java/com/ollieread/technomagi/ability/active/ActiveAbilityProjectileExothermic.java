package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityCast;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseTarget;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

public class ActiveAbilityProjectileExothermic extends AbilityActive
{

    protected Map<String, Integer> enhancements;
    protected int cost;

    public ActiveAbilityProjectileExothermic(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("force", 3);
        this.enhancements.put("exo", 1);
        this.cost = Config.projectileExothermicCost;

        this.knowledge = new String[] { "projectile", "exothermic" };
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
        return charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH) && cast.target.equals(AbilityUseTarget.AIR);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityCast cast, ItemStack staff)
    {
        int j = 72000;

        boolean flag = charon.player.capabilities.isCreativeMode;
        Random rand = new Random();

        if (flag) {
            if (charon.player.inventory.hasItem(Items.arrow) && charon.nanites.decreaseNanites(cost)) {
                float f = (float) j / 20.0F;
                f = (f * f + f * 2.0F) / 3.0F;

                if ((double) f < 0.1D) {
                    return false;
                }

                if (f > 1.0F) {
                    f = 1.0F;
                }

                EntityArrow entityarrow = new EntityArrow(charon.player.worldObj, charon.player, f * 2.0F);

                if (f == 1.0F) {
                    entityarrow.setIsCritical(true);
                }

                entityarrow.setFire(100);

                if (flag) {
                    entityarrow.canBePickedUp = 2;
                } else {
                    charon.player.inventory.consumeInventoryItem(Items.arrow);
                }

                charon.player.worldObj.spawnEntityInWorld(entityarrow);

                return true;
            }
        }

        return false;
    }

}
