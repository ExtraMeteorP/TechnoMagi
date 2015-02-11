package com.ollieread.technomagi.api.scan;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

import com.ollieread.technomagi.api.knowledge.research.IResearcher;

/**
 * Interface for handling block events.
 * 
 * This is called by scanner blocks to perform various actions and research.
 * 
 * @author ollieread
 *
 */
public interface IScanBlock
{

    /**
     * Handles block break events.
     * 
     * @see BreakEvent
     * @param x
     * @param y
     * @param z
     * @param block
     * @param metadata
     */
    public void onBreak(IResearcher handler, World world, int x, int y, int z, Block block, int metadata);

    /**
     * Handles block placement events.
     * 
     * @see PlaceEvent
     * @param x
     * @param y
     * @param z
     * @param block
     * @param metadata
     * @param againsBlock
     * @param againstMetadata
     */
    public void onPlace(IResearcher handler, World world, int x, int y, int z, Block block, int metadata);

    /**
     * Handles left clicking block events.
     * 
     * @see PlayerInteractEvent
     * @param x
     * @param y
     * @param z
     * @param block
     * @param metadata
     */
    public void onLeftClick(IResearcher handler, World world, int x, int y, int z, Block block, int metadata);

    /**
     * Handles right clicking block events.
     * 
     * @see PlayerInteractEvent
     * @param x
     * @param y
     * @param z
     * @param block
     * @param metadata
     */
    public void onRightClick(IResearcher handler, World world, int x, int y, int z, Block block, int metadata);

    /**
     * Handles harvest block events.
     * 
     * @see HarvestDropsEvent
     * @param x
     * @param y
     * @param z
     * @param block
     * @param metadata
     */
    public void onHarvest(IResearcher handler, World world, int x, int y, int z, Block block, int metadata);

    /**
     * Handles block updates, typically changes to metadata and replacements.
     * 
     * @param handler
     * @param world
     * @param x
     * @param y
     * @param z
     * @param block
     * @param metadata
     */
    public void onChange(IResearcher handler, World world, int x, int y, int z, Block block, int metadata);

    /**
     * Handles TileEntity changes.
     * 
     * @param handler
     * @param world
     * @param x
     * @param y
     * @param z
     * @param block
     * @param metadata
     * @param tile
     */
    public void onTileChange(IResearcher handler, World world, int x, int y, int z, Block block, int metadata, TileEntity tile);

}
