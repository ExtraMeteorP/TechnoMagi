package com.ollieread.technomagi.ability.active;

import java.util.Random;

import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.util.EntityHelper;
import com.ollieread.technomagi.util.SoundHelper;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityBlink extends AbilityActive
{

    public ActiveAbilityBlink(String name)
    {
        super(name, Reference.MODID.toLowerCase());
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event)
    {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;

            if (!interact.action.equals(Action.LEFT_CLICK_BLOCK)) {
                Vec3 look = EntityHelper.getLookVector(interact.entityPlayer);
                Vec3 eye = EntityHelper.getEyeVector(interact.entityPlayer);

                Vec3 target = Vec3.createVectorHelper(look.xCoord, look.yCoord, look.zCoord);
                Vec3 dest = null;

                Random rand = new Random();

                target.xCoord = (look.xCoord * 15) + eye.xCoord;
                target.yCoord = (look.yCoord * 15) + eye.yCoord;
                target.zCoord = (look.zCoord * 15) + eye.zCoord;

                EnderTeleportEvent teleportEvent = new EnderTeleportEvent(interact.entityPlayer, target.xCoord, target.yCoord, target.zCoord, 0);
                boolean cancelled = MinecraftForge.EVENT_BUS.post(teleportEvent);

                if (!cancelled && decreaseNanites(charon, 10)) {
                    for (int i = 0; i < 32; ++i) {
                        interact.entityPlayer.worldObj.spawnParticle("portal", teleportEvent.targetX, teleportEvent.targetY + rand.nextDouble() * 2.0D, teleportEvent.targetZ, rand.nextGaussian(), 0.0D, rand.nextGaussian());
                    }
                    interact.entityPlayer.setPositionAndUpdate(teleportEvent.targetX, teleportEvent.targetY, teleportEvent.targetZ);
                    SoundHelper.playSoundEffectAtPlayer(interact.entityPlayer, "cast", rand);

                    return true;
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
        return new String[] { "teleportationI" };
    }

}
