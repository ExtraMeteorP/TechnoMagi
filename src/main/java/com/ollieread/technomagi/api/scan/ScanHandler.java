package com.ollieread.technomagi.api.scan;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

import com.ollieread.technomagi.api.util.BlockRepresentation;
import com.ollieread.technomagi.api.util.ItemStackRepresentation;

/**
 * Handles the scanning registry.
 * 
 * @author ollieread
 *
 */
public class ScanHandler
{

    protected Map<ItemStackRepresentation, List<String>> itemResearchMapping = new LinkedHashMap<ItemStackRepresentation, List<String>>();
    protected Map<BlockRepresentation, IScanBlock> blockScanHandler = new LinkedHashMap<BlockRepresentation, IScanBlock>();

    protected List<ScanRepresentation> activeScanners = new ArrayList<ScanRepresentation>();

    /**
     * Get an instance of {@link ItemStackRepresentation} for the provided
     * ItemStack.
     * 
     * @param stack
     * @return
     */
    public static ItemStackRepresentation getItemStackRepresentation(ItemStack stack)
    {
        return new ItemStackRepresentation(stack);
    }

    /**
     * Get an instance of {@link ItemStackRepresentation} for the provided Item
     * and damage value.
     * 
     * @param item
     * @param damage
     * @return
     */
    public static ItemStackRepresentation getItemStackRepresentation(Item item, int damage)
    {
        return getItemStackRepresentation(new ItemStack(item, 1, damage));
    }

    /**
     * Get an instance of {@link BlockRepresentation} for the provided
     * ItemStack.
     * 
     * @param stack
     * @return
     */
    public static BlockRepresentation getBlockRepresentation(ItemStack stack)
    {
        return new BlockRepresentation(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage());
    }

    /**
     * Get an instance of {@link BlockRepresentation} for the provided block and
     * metadata.
     * 
     * @param block
     * @param metadata
     * @return
     */
    public static BlockRepresentation getBlockRepresentation(Block block, int metadata)
    {
        return new BlockRepresentation(block, metadata);
    }

    /**
     * 
     * @param item
     * @param research
     */
    public void addItemStackScanMapping(ItemStackRepresentation item, String research)
    {
        if (!(itemResearchMapping.containsKey(item))) {
            itemResearchMapping.put(item, new ArrayList<String>());
        }

        itemResearchMapping.get(item).add(research);
    }

    /**
     * 
     * @param item
     * @param research
     */
    public void addItemStackScanMapping(ItemStackRepresentation item, String[] research)
    {
        itemResearchMapping.put(item, Arrays.asList(research));
    }

    public List<String> getItemStackScanMapping(ItemStackRepresentation item)
    {
        if (itemResearchMapping.containsKey(item)) {
            return itemResearchMapping.get(item);
        }

        return new ArrayList<String>();
    }

    /**
     * 
     * @param block
     * @param scan
     */
    public void addBlockScanHandler(BlockRepresentation block, IScanBlock scan)
    {
        blockScanHandler.put(block, scan);
    }

    /**
     * 
     * @param block
     * @return
     */
    public IScanBlock getBlockScanHandler(BlockRepresentation block)
    {
        if (blockScanHandler.containsKey(block)) {
            return blockScanHandler.get(block);
        }

        return null;
    }

    public void addScanner(ScanRepresentation scanner)
    {
        if (!activeScanners.contains(scanner)) {
            activeScanners.add(scanner);
        }
    }

    public void removeScanner(ScanRepresentation scanner)
    {
        if (activeScanners.contains(scanner)) {
            activeScanners.remove(scanner);
        }
    }

    public boolean isScannerWatching(int x, int y, int z)
    {
        return getWatchingScanner(x, y, z) != null;
    }

    public ScanRepresentation getWatchingScanner(int x, int y, int z)
    {
        for (ScanRepresentation scanner : activeScanners) {
            if (scanner.isCovering(x, y, z)) {
                return scanner;
            }
        }

        return null;
    }

    /**
     * This class represents a scanner. This is used to identify which blocks
     * are being watched for events.
     * 
     * @author ollieread
     *
     */
    public static class ScanRepresentation
    {

        protected int focusX;
        protected int focusY;
        protected int focusZ;

        protected WeakReference<IScanTile> tile;

        public ScanRepresentation(IScanTile tile, int x, int y, int z)
        {
            this.focusX = x;
            this.focusY = y;
            this.focusZ = z;

            this.tile = new WeakReference<IScanTile>(tile);
        }

        public boolean isCovering(int x, int y, int z)
        {
            return focusX == x && focusY == y && focusZ == z;
        }

        @Override
        public int hashCode()
        {
            return focusX + focusZ << 8 + focusY << 16;
        }

        public IScanTile getScanner()
        {
            if (tile.get() != null) {
                return tile.get();
            }

            return null;
        }

    }

}
