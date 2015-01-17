package com.ollieread.technomagi.event.handler;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;

import com.ollieread.technomagi.tileentity.ITileEntityPocket;
import com.ollieread.technomagi.util.WorldHelper;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PocketEventHandler
{

    @SubscribeEvent
    public void onBonemeal(BonemealEvent event)
    {
        if (!event.world.isRemote) {
            List<TileEntity> tileEntities = WorldHelper.getTileEntitiesWithin(event.world, event.x - 16, event.y - 16, event.z - 16, event.x + 16, event.y + 16, event.z + 16);

            for (TileEntity tileEntity : tileEntities) {
                if (tileEntity instanceof ITileEntityPocket) {
                    int distance = (int) WorldHelper.getDistance(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, event.x, event.y, event.z);

                    if (distance <= (((ITileEntityPocket) tileEntity).getSize() / 2)) {
                        if (((ITileEntityPocket) tileEntity).isNegative()) {
                            event.setResult(Result.DENY);
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSaplingGrow(SaplingGrowTreeEvent event)
    {
        if (!event.world.isRemote) {
            List<TileEntity> tileEntities = WorldHelper.getTileEntitiesWithin(event.world, event.x - 16, event.y - 16, event.z - 16, event.x + 16, event.y + 16, event.z + 16);

            for (TileEntity tileEntity : tileEntities) {
                if (tileEntity instanceof ITileEntityPocket) {
                    int distance = (int) WorldHelper.getDistance(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, event.x, event.y, event.z);

                    if (distance <= (((ITileEntityPocket) tileEntity).getSize() / 2)) {
                        if (((ITileEntityPocket) tileEntity).isNegative()) {
                            event.setResult(Result.DENY);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (!event.world.isRemote) {
            List<TileEntity> tileEntities = WorldHelper.getTileEntitiesWithin(event.world, event.x - 16, event.y - 16, event.z - 16, event.x + 16, event.y + 16, event.z + 16);

            for (TileEntity tileEntity : tileEntities) {
                if (tileEntity instanceof ITileEntityPocket) {
                    int distance = (int) WorldHelper.getDistance(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, event.x, event.y, event.z);

                    if (distance <= (((ITileEntityPocket) tileEntity).getSize() / 2)) {
                        if (((ITileEntityPocket) tileEntity).isNegative()) {
                            ItemStack heldItem = event.entityPlayer.getCurrentEquippedItem();

                            if (heldItem != null && heldItem.getItem() != null) {
                                Block block = Block.getBlockFromItem(heldItem.getItem());

                                if (block != null) {
                                    if (!(block instanceof IPlantable)) {
                                        event.setCanceled(true);
                                        event.setResult(Result.DENY);
                                        event.useBlock = Result.DENY;
                                        event.useItem = Result.DENY;
                                    }
                                } else if (heldItem.getItem() instanceof IPlantable) {
                                    event.setCanceled(true);
                                    event.setResult(Result.DENY);
                                    event.useBlock = Result.DENY;
                                    event.useItem = Result.DENY;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
