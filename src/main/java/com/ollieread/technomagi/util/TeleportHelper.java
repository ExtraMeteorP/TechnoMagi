package com.ollieread.technomagi.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityTeleporter;

public class TeleportHelper
{

    public static TileEntityTeleporter findTeleporterAbove(TileEntityTeleporter teleporter, EntityLivingBase entity)
    {
        int ox = teleporter.xCoord;
        int oy = teleporter.yCoord + 3;
        int oz = teleporter.zCoord;

        if (oy >= 251)
            return null;

        for (int i = 0; (i + oy) < 251; i++) {
            TileEntity t = teleporter.getWorldObj().getTileEntity(ox, oy + i, oz);

            if (t instanceof TileEntityTeleporter) {
                TileEntityTeleporter te = (TileEntityTeleporter) t;

                if (te.canUse(entity) && teleporter.getWorldObj().getBlockMetadata(ox, oy + i, oz) == 0) {
                    return te;
                }
            }
        }

        return null;
    }

    public static TileEntityTeleporter findTeleporterBelow(TileEntityTeleporter teleporter, EntityLivingBase entity)
    {
        int ox = teleporter.xCoord;
        int oy = teleporter.yCoord - 3;
        int oz = teleporter.zCoord;

        if (oy < 3)
            return null;

        for (int i = 0; (oy - i) >= 3; i++) {
            TileEntity t = teleporter.getWorldObj().getTileEntity(ox, oy - i, oz);

            if (t instanceof TileEntityTeleporter) {
                TileEntityTeleporter te = (TileEntityTeleporter) t;

                if (te.canUse(entity) && teleporter.getWorldObj().getBlockMetadata(ox, oy - i, oz) == 0) {
                    return te;
                }
            }
        }

        return null;
    }

    public static void teleportPlayerTo(EntityPlayer player, double x, double y, double z)
    {
        player.setPositionAndUpdate(x, y, z);
        player.worldObj.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, Reference.MODID.toLowerCase() + ":teleport", 0.5F, player.worldObj.rand.nextFloat() * 0.4F + 0.8F);
    }

    public static void teleportPlayerToTeleporter(EntityPlayer player, TileEntityTeleporter location, TileEntityTeleporter destination)
    {
        destination.startCooldown();
        teleportPlayerTo(player, destination.xCoord + 0.5D, destination.yCoord + 1, destination.zCoord + 0.5D);
    }

    public static void teleportEntityTo(EntityLivingBase entity, double x, double y, double z)
    {
        entity.setPositionAndUpdate(x, y, z);
        entity.worldObj.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, Reference.MODID.toLowerCase() + ":teleport", 0.5F, entity.worldObj.rand.nextFloat() * 0.4F + 0.8F);
    }

    public static void teleportEntityToTeleporter(EntityLivingBase entity, TileEntityTeleporter location, TileEntityTeleporter destination)
    {
        destination.startCooldown();
        teleportEntityTo(entity, destination.xCoord + 0.5D, destination.yCoord + 1, destination.zCoord + 0.5D);
    }

}
