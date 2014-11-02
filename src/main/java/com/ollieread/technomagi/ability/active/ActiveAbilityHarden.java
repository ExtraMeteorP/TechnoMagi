package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Potions;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityHarden extends AbilityActive
{
    protected Map<String, Integer> enhancements;

    public ActiveAbilityHarden(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        this.enhancements = new HashMap<String, Integer>();
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event)
    {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;

            if (interact.action.equals(Action.RIGHT_CLICK_AIR) && !interact.entityPlayer.isPotionActive(Potions.potionHardness)) {
                if (decreaseNanites(charon, 10)) {
                    if (!interact.entityPlayer.worldObj.isRemote) {
                        Random rand = new Random();

                        interact.entityPlayer.worldObj.playSoundEffect((double) interact.entityPlayer.posX + 0.5D, (double) interact.entityPlayer.posY + 0.5D, (double) interact.entityPlayer.posZ + 0.5D, Reference.MODID.toLowerCase() + ":cast", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                        interact.entityPlayer.addPotionEffect(new PotionEffect(Potions.potionHardness.id, 400, 0, false));
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
        return null;
    }

    @Override
    public String[] getKnowledge()
    {
        return new String[] { "density" };
    }

}
