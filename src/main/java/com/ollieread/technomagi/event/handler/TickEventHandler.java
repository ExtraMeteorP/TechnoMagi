package com.ollieread.technomagi.event.handler;

import com.ollieread.ennds.ability.AbilityRegistry;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityTeleporter;
import com.ollieread.technomagi.util.EntityHelper;
import com.ollieread.technomagi.util.TeleportHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class TickEventHandler
{

    @SubscribeEvent
    public void onPlayerTickEvent(PlayerTickEvent event)
    {
        if (!event.player.worldObj.isRemote && event.phase.equals(TickEvent.Phase.END)) {
            AbilityRegistry.passiveAbilityEvent("playerTick", event, ExtendedPlayerKnowledge.get(event.player));

            if (event.player.isSneaking()) {
                if (EntityHelper.isStoodOnMeta(event.player, Blocks.blockTeleporter, 0)) {
                    TileEntityTeleporter teleporter = (TileEntityTeleporter) EntityHelper.getTileEntityStoodOn(event.player);

                    if (teleporter != null && teleporter.canUse(event.player)) {
                        TileEntityTeleporter destination = TeleportHelper.findTeleporterBelow(teleporter, event.player);

                        if (destination != null && destination.canUse(event.player)) {
                            TeleportHelper.teleportPlayerToTeleporter(event.player, teleporter, destination);
                        }
                    }
                }
            }
        }
    }
}
