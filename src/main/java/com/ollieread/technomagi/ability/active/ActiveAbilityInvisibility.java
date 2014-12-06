package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.item.IStaff;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityInvisibility extends AbilityActive
{

    protected Map<String, Integer> enhancements;
    protected int cost;
    protected int duration;

    public ActiveAbilityInvisibility(String name)
    {
        super(name, Reference.MODID.toLowerCase());
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("light", 1);
        this.cost = Config.invisibilityCost;
        this.duration = Config.invisibilityDuration;
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event, ItemStack stack)
    {
        int level = ((IStaff) stack.getItem()).getEnhancement(stack, "light");

        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;

            if (interact.action.equals(Action.RIGHT_CLICK_AIR) && !interact.entityPlayer.isPotionActive(Potion.invisibility)) {
                if (decreaseNanites(charon, cost)) {
                    if (!interact.entityPlayer.worldObj.isRemote) {
                        Random rand = new Random();

                        interact.entityPlayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, duration * level, level, false));
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
        return new String[] { "light" };
    }

}
