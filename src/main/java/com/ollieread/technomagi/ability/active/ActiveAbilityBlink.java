package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.util.EntityHelper;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityBlink extends AbilityActive
{
    protected Map<String, Integer> enhancements;
    protected int cost;
    protected int distance;

    public ActiveAbilityBlink(String name)
    {
        super(name, Reference.MODID.toLowerCase());
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("teleport", 1);
        this.cost = Config.blinkCost;
        this.distance = Config.blinkDistance;
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event, ItemStack stack)
    {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;

            if (!interact.action.equals(Action.LEFT_CLICK_BLOCK)) {
                Vec3 look = EntityHelper.getLookVector(interact.entityPlayer);
                Vec3 eye = EntityHelper.getEyeVector(interact.entityPlayer);

                Vec3 target = Vec3.createVectorHelper(look.xCoord, look.yCoord, look.zCoord);
                Vec3 dest = null;

                Random rand = new Random();

                target.xCoord = (look.xCoord * distance) + eye.xCoord;
                target.yCoord = (look.yCoord * distance) + eye.yCoord;
                target.zCoord = (look.zCoord * distance) + eye.zCoord;

                EnderTeleportEvent teleportEvent = new EnderTeleportEvent(interact.entityPlayer, target.xCoord, target.yCoord, target.zCoord, 0);
                boolean cancelled = MinecraftForge.EVENT_BUS.post(teleportEvent);

                if (!cancelled && decreaseNanites(charon, cost)) {
                    for (int i = 0; i < 32; ++i) {
                        interact.entityPlayer.worldObj.spawnParticle("portal", teleportEvent.targetX, teleportEvent.targetY + rand.nextDouble() * 2.0D, teleportEvent.targetZ, rand.nextGaussian(), 0.0D, rand.nextGaussian());
                    }

                    if (!interact.entityPlayer.worldObj.isRemote) {
                        interact.entityPlayer.setPositionAndUpdate(teleportEvent.targetX, teleportEvent.targetY, teleportEvent.targetZ);
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
        return new String[] { "teleportation" };
    }

}
