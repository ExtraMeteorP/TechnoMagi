package com.ollieread.technomagi.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporter;

public class TeleportHelper
{

    public static void teleportPlayerTo(EntityPlayer player, int x, int y, int z, boolean event)
    {
        if (event) {
            EnderTeleportEvent teleportEvent = new EnderTeleportEvent(player, x, y, z, 0);

            if (MinecraftForge.EVENT_BUS.post(teleportEvent)) {
                return;
            }

            x = (int) teleportEvent.targetX;
            y = (int) teleportEvent.targetY;
            z = (int) teleportEvent.targetZ;
        }

        player.setPositionAndUpdate(x, y, z);
    }

    public static void teleportPlayerToTeleporter(EntityPlayer player, int x, int y, int z, boolean event)
    {
        TileEntity teleporter = player.worldObj.getTileEntity(x, y, z);

        if (teleporter != null && teleporter instanceof TileTeleporter) {
            teleportPlayerTo(player, x, y, z, event);
            ((TileTeleporter) teleporter).startCooldown(true);
        }
    }

}
