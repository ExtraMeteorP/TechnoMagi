package com.ollieread.technomagi.ability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import com.ollieread.technomagi.api.ability.AbilityPassive;
import com.ollieread.technomagi.api.research.ResearchEvents;
import com.ollieread.technomagi.player.PlayerKnowledge;
import com.ollieread.technomagi.util.ExplosionHelper;

public class PassiveAbilityNegateFall extends AbilityPassive<LivingFallEvent>
{

    public PassiveAbilityNegateFall(String name)
    {
        super(name);
    }

    @Override
    public int getEvent()
    {
        return ResearchEvents.EVENT_FALL;
    }

    @Override
    public boolean canUse(PlayerKnowledge charon)
    {
        return true;
    }

    @Override
    public boolean isAvailable(PlayerKnowledge charon)
    {
        return true;
    }

    @Override
    public void use(LivingFallEvent event, PlayerKnowledge charon)
    {
        int fall = Math.round(event.distance);
        boolean flag = false;
        float size = 0;

        if (charon.decreaseNanites(fall)) {
            event.distance -= fall;
            flag = true;
            size = fall / 15;
        } else {
            int nanites = charon.getNanites();

            if (charon.decreaseNanites(nanites)) {
                event.distance -= nanites;
                flag = true;
                size = nanites / 15;
            }
        }

        if (flag && size > 0) {
            EntityPlayer player = (EntityPlayer) event.entity;
            ExplosionHelper.newFallExplosion(player.worldObj, player, player.posX, player.posY, player.posZ, size * 2);
        }
    }
}
