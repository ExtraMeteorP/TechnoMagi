package com.ollieread.technomagi.api.scan;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import scala.actors.threadpool.Arrays;

import com.ollieread.technomagi.util.BlockRepresentation;
import com.ollieread.technomagi.util.ItemStackRepresentation;

/**
 * Handles the scanning registry.
 *
 * @author ollieread
 *
 */
public class ScanHandler
{

    protected Map<ItemStackRepresentation, List<String>> itemScanResearchMapping = new LinkedHashMap<ItemStackRepresentation, List<String>>();
    protected Map<BlockRepresentation, List<String>> blockScanResearchMapping = new LinkedHashMap<BlockRepresentation, List<String>>();
    protected Map<ItemStackRepresentation, List<String>> itemAnalyseResearchMapping = new LinkedHashMap<ItemStackRepresentation, List<String>>();
    protected Map<BlockRepresentation, IScanBlock> blockScanHandler = new LinkedHashMap<BlockRepresentation, IScanBlock>();

    protected List<ScanRepresentation> activeScanners = new ArrayList<ScanRepresentation>();

    /**
     *
     * @param item
     * @param research
     */
    public void addScanMapping(ItemStackRepresentation item, String research)
    {
        if (!(itemScanResearchMapping.containsKey(item))) {
            itemScanResearchMapping.put(item, new ArrayList<String>());
        }

        itemScanResearchMapping.get(item).add(research);
    }

    /**
     *
     * @param item
     * @param research
     */
    public void addScanMapping(ItemStackRepresentation item, String[] research)
    {
        itemScanResearchMapping.put(item, Arrays.asList(research));
    }

    public List<String> getScanMapping(ItemStackRepresentation item)
    {
        if (itemScanResearchMapping.containsKey(item)) {
            return itemScanResearchMapping.get(item);
        }

        return new ArrayList<String>();
    }

    /**
     *
     * @param item
     * @param research
     */
    public void addScanMapping(BlockRepresentation item, String research)
    {
        if (!(blockScanResearchMapping.containsKey(item))) {
            blockScanResearchMapping.put(item, new ArrayList<String>());
        }

        blockScanResearchMapping.get(item).add(research);
    }

    /**
     *
     * @param item
     * @param research
     */
    public void addScanMapping(BlockRepresentation item, String[] research)
    {
        blockScanResearchMapping.put(item, Arrays.asList(research));
    }

    public List<String> getScanMapping(BlockRepresentation item)
    {
        if (blockScanResearchMapping.containsKey(item)) {
            return blockScanResearchMapping.get(item);
        }

        return new ArrayList<String>();
    }

    /**
     *
     * @param item
     * @param research
     */
    public void addAnalysisMapping(ItemStackRepresentation item, String research)
    {
        if (!(itemAnalyseResearchMapping.containsKey(item))) {
            itemAnalyseResearchMapping.put(item, new ArrayList<String>());
        }

        itemAnalyseResearchMapping.get(item).add(research);
    }

    /**
     *
     * @param item
     * @param research
     */
    public void addAnalysisMapping(ItemStackRepresentation item, String[] research)
    {
        itemAnalyseResearchMapping.put(item, Arrays.asList(research));
    }

    public List<String> getAnalysisMapping(ItemStackRepresentation item)
    {
        if (itemAnalyseResearchMapping.containsKey(item)) {
            return itemAnalyseResearchMapping.get(item);
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
