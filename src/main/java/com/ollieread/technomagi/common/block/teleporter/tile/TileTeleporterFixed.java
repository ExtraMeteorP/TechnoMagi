package com.ollieread.technomagi.common.block.teleporter.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.ollieread.technomagi.util.TeleportHelper;

public class TileTeleporterFixed extends TileTeleporter
{
    protected int range = 16;

    @Override
    public void startCooldown(boolean incoming)
    {
        setCooldown(25);
    }

    @Override
    public boolean canUse(EntityPlayer player, boolean incoming)
    {
        if (super.canUse(player, incoming)) {
            if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord) && worldObj.isAirBlock(xCoord, yCoord + 2, zCoord)) {
                if (isPlayerLocked()) {
                    return !player.getCommandSenderName().equals(player) ? false : true;
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public void use(EntityPlayer player, boolean incoming)
    {
        for (int i = 0; i < range; i++) {
            TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1 + i, zCoord);

            if (tile instanceof TileTeleporterFixed) {
                if (((TileTeleporterFixed) tile).canUse(player, !incoming)) {
                    TeleportHelper.teleportEntityToTeleporter(player, tile.xCoord, tile.yCoord, tile.zCoord, false);
                    break;
                }
            }
        }
    }

}
