package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.IAbilityActiveCast;
import com.ollieread.ennds.event.EnndsEvent.PlayerCastingEvent;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityDrain extends AbilityActive implements IAbilityActiveCast
{

    protected Map<String, Integer> enhancements;
    protected int cost;

    public ActiveAbilityDrain(String name)
    {
        super(name, Reference.MODID.toLowerCase());
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("life", 2);
        this.cost = Config.harvestCost;
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event, ItemStack stack)
    {
        if (event instanceof PlayerCastingEvent) {
            PlayerCastingEvent casting = (PlayerCastingEvent) event;
            EntityPlayer player = casting.entityPlayer;
            MovingObjectPosition mouse = Minecraft.getMinecraft().objectMouseOver;

            if (player.getHealth() < player.getMaxHealth() && mouse.typeOfHit.equals(MovingObjectType.ENTITY)) {
                Entity entity = mouse.entityHit;

                if (entity instanceof EntityLiving) {
                    EntityLiving entityLiving = (EntityLiving) entity;

                    if (entityLiving.isEntityAlive()) {
                        int h = 10;
                        if (casting.duration > h) {
                            boolean n = (casting.duration % h) == 0;

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
            } else {
                return true;
            }
        }

        return false;
    }

    @Override
    public Map<String, Integer> getEnhancements()
    {
        return enhancements;
    }

    @Override
    public String[] getKnowledge()
    {
        return new String[] { "density", "force" };
    }

    @Override
    public int getCast()
    {
        return 0;
    }
}
