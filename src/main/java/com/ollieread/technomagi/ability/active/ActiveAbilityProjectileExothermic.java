package com.ollieread.technomagi.ability.active;

import java.util.Random;

import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityProjectileExothermic extends AbilityActive
{

    public ActiveAbilityProjectileExothermic(String name)
    {
        super(name, Reference.MODID.toLowerCase());
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event)
    {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;

            if (interact.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_AIR)) {
                int j = 72000;

                boolean flag = charon.player.capabilities.isCreativeMode;
                Random rand = new Random();

                if (flag) {
                    if (charon.player.inventory.hasItem(Items.arrow) && charon.nanites.decreaseNanites(7)) {
                        float f = (float) j / 20.0F;
                        f = (f * f + f * 2.0F) / 3.0F;

                        if ((double) f < 0.1D) {
                            return false;
                        }

                        if (f > 1.0F) {
                            f = 1.0F;
                        }

                        EntityArrow entityarrow = new EntityArrow(interact.world, charon.player, f * 2.0F);

                        if (f == 1.0F) {
                            entityarrow.setIsCritical(true);
                        }

                        entityarrow.setFire(100);

                        if (flag) {
                            entityarrow.canBePickedUp = 2;
                        } else {
                            charon.player.inventory.consumeInventoryItem(Items.arrow);
                        }

                        interact.world.spawnEntityInWorld(entityarrow);
                    }
                }
            }
        }

        return false;
    }

    @Override
    public String[] getEnhancements()
    {
        return null;
    }

    @Override
    public String[] getKnowledge()
    {
        return new String[] { "projectile", "exothermic" };
    }

}
