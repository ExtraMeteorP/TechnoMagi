package com.ollieread.technomagi.util;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.tileentity.TileEntityTeleporter;

public class TeleportHelper
{

    public static TileEntityTeleporter findTeleporterAbove(TileEntityTeleporter teleporter)
    {
        int ox = teleporter.xCoord;
        int oy = teleporter.yCoord + 3;
        int oz = teleporter.zCoord;

        if (oy >= 251)
            return null;

        for (int i = 0; (i + oy) < 251; i++) {
            TileEntityTeleporter t = (TileEntityTeleporter) teleporter.getWorldObj().getTileEntity(ox, oy + i, oz);

            if (t instanceof TileEntityTeleporter && t.canUse()) {
                return t;
            }
        }

        return null;
    }

    public static TileEntityTeleporter findTeleporterBelow(TileEntityTeleporter teleporter)
    {
        int ox = teleporter.xCoord;
        int oy = teleporter.yCoord - 3;
        int oz = teleporter.zCoord;

        if (oy < 3)
            return null;

        for (int i = 0; (oy - i) >= 3; i++) {
            TileEntityTeleporter t = (TileEntityTeleporter) teleporter.getWorldObj().getTileEntity(ox, oy - i, oz);

            if (t instanceof TileEntityTeleporter && t.canUse()) {
                return t;
            }
        }

        return null;
    }

    public static void teleportPlayerTo(EntityPlayer player, double x, double y, double z)
    {
        player.setPositionAndUpdate(x, y, z);
    }

    public static void teleportPlayerToTeleporter(EntityPlayer player, TileEntityTeleporter location, TileEntityTeleporter destination)
    {
        teleportPlayerTo(player, destination.xCoord + 0.5D, destination.yCoord + 1, destination.zCoord + 0.5D);
        destination.startCooldown();
    }

}
