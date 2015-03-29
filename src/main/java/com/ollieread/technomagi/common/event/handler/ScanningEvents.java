package com.ollieread.technomagi.common.event.handler;

import net.minecraft.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.scan.IScanBlock;
import com.ollieread.technomagi.api.scan.IScanTile;
import com.ollieread.technomagi.api.scan.ScanHandler.ScanRepresentation;
import com.ollieread.technomagi.util.BlockHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ScanningEvents
{

    @SubscribeEvent
    public void onBreak(BreakEvent event)
    {
        if (!event.world.isRemote) {
            ScanRepresentation scanner = TechnomagiApi.scan().getWatchingScanner(event.x, event.y, event.z);
            if (scanner != null) {
                IScanTile tile = scanner.getScanner();

                if (tile != null) {
                    IScanBlock block = TechnomagiApi.scan().getBlockScanHandler(BlockHelper.getBlockRepresentation(event.block, event.blockMetadata));

                    if (block != null) {
                        block.onBreak(tile, event.world, event.x, event.y, event.z, event.block, event.blockMetadata);
                    }
                } else {
                    TechnomagiApi.scan().removeScanner(scanner);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlace(PlaceEvent event)
    {
        if (!event.world.isRemote) {
            ScanRepresentation scanner = TechnomagiApi.scan().getWatchingScanner(event.x, event.y, event.z);
            if (scanner != null) {
                IScanTile tile = scanner.getScanner();

                if (tile != null) {
                    IScanBlock block = TechnomagiApi.scan().getBlockScanHandler(BlockHelper.getBlockRepresentation(event.block, event.blockMetadata));

                    if (block != null) {
                        block.onPlace(tile, event.world, event.x, event.y, event.z, event.block, event.blockMetadata);
                    }
                } else {
                    TechnomagiApi.scan().removeScanner(scanner);
                }
            }
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event)
    {
        if (!event.world.isRemote && (event.action.equals(Action.LEFT_CLICK_BLOCK) || event.action.equals(Action.RIGHT_CLICK_BLOCK))) {
            ScanRepresentation scanner = TechnomagiApi.scan().getWatchingScanner(event.x, event.y, event.z);
            if (scanner != null) {
                IScanTile tile = scanner.getScanner();

                if (tile != null) {
                    Block worldBlock = event.world.getBlock(event.x, event.y, event.z);
                    int metadata = event.world.getBlockMetadata(event.x, event.y, event.z);
                    IScanBlock block = TechnomagiApi.scan().getBlockScanHandler(BlockHelper.getBlockRepresentation(worldBlock, metadata));

                    if (block != null) {
                        if (event.action.equals(Action.LEFT_CLICK_BLOCK)) {
                            block.onLeftClick(tile, event.world, event.x, event.y, event.z, worldBlock, metadata);
                        } else if (event.action.equals(Action.RIGHT_CLICK_BLOCK)) {
                            block.onRightClick(tile, event.world, event.x, event.y, event.z, worldBlock, metadata);
                        }
                    }
                } else {
                    TechnomagiApi.scan().removeScanner(scanner);
                }
            }
        }
    }

    @SubscribeEvent
    public void onHarvest(HarvestDropsEvent event)
    {
        if (!event.world.isRemote) {
            ScanRepresentation scanner = TechnomagiApi.scan().getWatchingScanner(event.x, event.y, event.z);
            if (scanner != null) {
                IScanTile tile = scanner.getScanner();

                if (tile != null) {
                    IScanBlock block = TechnomagiApi.scan().getBlockScanHandler(BlockHelper.getBlockRepresentation(event.block, event.blockMetadata));

                    if (block != null) {
                        block.onHarvest(tile, event.world, event.x, event.y, event.z, event.block, event.blockMetadata);
                    }
                } else {
                    TechnomagiApi.scan().removeScanner(scanner);
                }
            }
        }
    }

}
