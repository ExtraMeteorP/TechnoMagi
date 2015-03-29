package com.ollieread.technomagi.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import com.ollieread.technomagi.common.block.teleporter.tile.TileElevator;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporter;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Config;

public class TeleportHelper
{

    public static void teleportEntityTo(EntityLivingBase entity, int x, int y, int z, boolean event)
    {
        if (event) {
            EnderTeleportEvent teleportEvent = new EnderTeleportEvent(entity, x, y, z, 0);

            if (MinecraftForge.EVENT_BUS.post(teleportEvent)) {
                return;
            }

            x = (int) teleportEvent.targetX;
            y = (int) teleportEvent.targetY;
            z = (int) teleportEvent.targetZ;
        }

        entity.setPositionAndUpdate(x, y, z);
    }

    public static void teleportEntityToTeleporter(EntityLivingBase entity, int x, int y, int z, boolean event)
    {
        TileEntity teleporter = entity.worldObj.getTileEntity(x, y, z);

        if (teleporter != null && teleporter instanceof TileTeleporter) {
            teleportEntityTo(entity, x, y, z, event);
            ((TileTeleporter) teleporter).startCooldown(true);
        }
    }

    public static void teleportToElevatorAbove(EntityLivingBase entity, int x, int y, int z, boolean event)
    {
        int dx = -1;
        int dy = -1;
        int dz = -1;

        int blocks = 0;
        y++;

        for (int i = 0; i < Config.elevatorLength; i++) {
            if (!entity.worldObj.isAirBlock(x, y + i, z)) {
                if (!(entity.worldObj.getBlock(x, y + i, z) == Blocks.elevator)) {
                    blocks++;

                    if (blocks == Config.elevatorBlockJump) {
                        break;
                    }
                } else {
                    TileElevator elevator = (TileElevator) entity.worldObj.getTileEntity(x, y + i, z);
                    elevator.startCooldown(true);
                    dx = x;
                    dy = y + i;
                    dz = z;
                    break;
                }
            }
        }

        if (dx != -1 && dy != -1 && dz != -1) {
            teleportEntityTo(entity, dx, dy, dz, true);
        }
    }

    public static void teleportToElevatorBelow(EntityLivingBase entity, int x, int y, int z, boolean event)
    {
        int dx = -1;
        int dy = -1;
        int dz = -1;

        int blocks = 0;
        y++;

        for (int i = 0; i < Config.elevatorLength; i++) {
            if ((y - i) == 0) {
                break;
            }

            if (!entity.worldObj.isAirBlock(x, y - i, z)) {
                if (!(entity.worldObj.getBlock(x, y - i, z) == Blocks.elevator)) {
                    blocks++;

                    if (blocks == Config.elevatorBlockJump) {
                        break;
                    }
                } else {
                    TileElevator elevator = (TileElevator) entity.worldObj.getTileEntity(x, y - i, z);
                    elevator.startCooldown(true);
                    dx = x;
                    dy = y - i;
                    dz = z;
                    break;
                }
            }
        }

        if (dx != -1 && dy != -1 && dz != -1) {
            teleportEntityTo(entity, dx, dy, dz, true);
        }
    }

}
