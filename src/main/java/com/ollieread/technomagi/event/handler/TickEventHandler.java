package com.ollieread.technomagi.event.handler;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.tileentity.TileEntityTeleporter;
import com.ollieread.technomagi.util.PlayerHelper;
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
            TMRegistry.passiveAbilityEvent("playerTick", event, ExtendedPlayerKnowledge.get(event.player));

            if (event.player.isSneaking()) {
                if (PlayerHelper.isStoodOnMeta(event.player, Blocks.blockTeleporter, 0)) {
                    TileEntityTeleporter teleporter = (TileEntityTeleporter) PlayerHelper.getTileEntityStoodOn(event.player);

                    if (teleporter != null && teleporter.canUse()) {
                        TileEntityTeleporter destination = TeleportHelper.findTeleporterBelow(teleporter);

                        if (destination != null && destination.canUse()) {
                            TeleportHelper.teleportPlayerToTeleporter(event.player, teleporter, destination);
                        }
                    }
                }
            }
        }
    }
}
