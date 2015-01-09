package com.ollieread.technomagi.util;

import java.util.HashMap;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ChunkHelper
{

    public static Chunk copyChunk(Chunk chunk, World world)
    {
        Chunk newChunk = new Chunk(world, chunk.xPosition, chunk.zPosition);

        newChunk.setStorageArrays(chunk.getBlockStorageArray());
        newChunk.setBiomeArray(chunk.getBiomeArray());
        newChunk.chunkTileEntityMap = new HashMap(chunk.chunkTileEntityMap);
        newChunk.precipitationHeightMap = chunk.precipitationHeightMap.clone();
        newChunk.updateSkylightColumns = chunk.updateSkylightColumns.clone();
        newChunk.isChunkLoaded = true;
        newChunk.heightMap = chunk.heightMap.clone();
        newChunk.isTerrainPopulated = chunk.isTerrainPopulated;
        newChunk.isLightPopulated = chunk.isLightPopulated;
        newChunk.field_150815_m = chunk.field_150815_m;
        newChunk.isModified = true;
        newChunk.hasEntities = false;
        newChunk.lastSaveTime = chunk.lastSaveTime;
        newChunk.sendUpdates = chunk.sendUpdates;
        newChunk.heightMapMinimum = chunk.heightMapMinimum;
        newChunk.inhabitedTime = chunk.inhabitedTime;

        return newChunk;
    }

}
