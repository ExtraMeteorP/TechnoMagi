package com.ollieread.technomagi.ability.active;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityCast;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseTarget;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

public class ActiveAbilityDrain extends AbilityActive
{

    protected int cost;

    public ActiveAbilityDrain(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        cost = Config.harvestCost;
    }

    @Override
    public int getMaxFocus()
    {
        return 200;
    }

    @Override
    public boolean canIntervalFocus()
    {
        return true;
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, AbilityCast cast)
    {
        return charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FOCUS_INTERVAL) && cast.target.equals(AbilityUseTarget.ENTITY_LIVING);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityCast cast, ItemStack staff)
    {
        EntityPlayer player = charon.player;

        if (player.getHealth() < player.getMaxHealth()) {
            EntityLiving entityLiving = cast.targetEntityLiving;

            if (entityLiving != null && entityLiving.isEntityAlive()) {
                int h = 10;
                if (cast.duration > h) {
                    boolean n = (cast.duration % h) == 0;

                    if (n) {
                        decreaseNanites(charon, 2);
                    }

                    int d = 2;

                    if ((player.getMaxHealth() - player.getHealth()) < 2) {
                        d = (int) (player.getMaxHealth() - player.getHealth());
                    }

                    if (n) {
                        int health = (int) entityLiving.getHealth();

                        if (health < d) {
                            d = health;
                        }

                        if (d > 0) {
                            if (!player.worldObj.isRemote) {
                                EntityDamageSource damage = new EntityDamageSource("drain", player);
                                entityLiving.attackEntityFrom(DamageSource.generic, (float) d);
                                player.heal(d);
                            }
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
