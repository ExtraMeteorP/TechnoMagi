package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.item.IStaff;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityHeal extends AbilityActive
{
    protected Map<String, Integer> enhancements;
    protected int cost;

    public ActiveAbilityHeal(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("life", 1);
        this.cost = Config.healCost;
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event, ItemStack stack)
    {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;

            if (interact.action.equals(Action.RIGHT_CLICK_AIR)) {
                if (decreaseNanites(charon, 10)) {
                    if (!interact.entityPlayer.worldObj.isRemote) {
                        float health = 3.0F;
                        int level = ((IStaff) stack.getItem()).getEnhancement(stack, "life");

                        if (level > 1) {
                            health += (level - 1) * 1F;
                        }

                        interact.entityPlayer.heal(health);
                    }

                    return true;
                }
            }
        } else if (event instanceof EntityInteractEvent) {
            EntityInteractEvent interact = (EntityInteractEvent) event;

            if (interact.target instanceof EntityLiving) {
                EntityLiving entity = (EntityLiving) interact.target;
                if (decreaseNanites(charon, cost)) {
                    if (!interact.entityPlayer.worldObj.isRemote) {
                        float health = 3.0F;
                        int level = ((IStaff) stack.getItem()).getEnhancement(stack, "life");

                        if (level > 1) {
                            health += (level - 1) * 1F;
                        }

                        entity.heal(health);
                    }

                    return true;
                }
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
        return new String[] {};
    }

}
