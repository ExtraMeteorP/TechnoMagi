package com.ollieread.technomagi.event.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.util.ChunkHelper;
import com.ollieread.technomagi.world.region.PerceptionManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ChunkEventHandler
{

    public static Map<Integer, List<Long>> copiedChunks = new HashMap<Integer, List<Long>>();

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event)
    {
        Chunk chunk = event.getChunk();
        World world = chunk.worldObj;

        if (!world.isRemote) {
            if (PerceptionManager.notPerceptionDimension(world.provider.dimensionId) && PerceptionManager.hasPerceptionDimension(world.provider.dimensionId)) {
                long i = ChunkCoordIntPair.chunkXZ2Int(chunk.xPosition, chunk.zPosition);
                int perceptionDimension = PerceptionManager.getForDimension(world.provider.dimensionId);
                List<Long> chunks = null;

                if (copiedChunks.containsKey(world.provider.dimensionId)) {
                    chunks = copiedChunks.get(world.provider.dimensionId);
                } else {
                    chunks = new ArrayList<Long>();
                }

                try {
                    WorldServer perception = MinecraftServer.getServer().worldServerForDimension(perceptionDimension);
                    ChunkProviderServer provider = perception.theChunkProviderServer;

                    if (provider.loadChunk(chunk.xPosition, chunk.zPosition) == null && !provider.chunkExists(chunk.xPosition, chunk.zPosition)) {
                        Chunk newChunk = ChunkHelper.copyChunk(chunk, perception);
                        provider.loadedChunkHashMap.add(i, newChunk);
                        chunks.add(i);
                    } else {
                        chunks.add(i);
                    }
                } catch (RuntimeException e) {
                    TechnoMagi.logger.log(Level.INFO, "Unable to copy chunk: " + i);
                }

                copiedChunks.put(world.provider.dimensionId, chunks);
            }
        }
    }

    @SubscribeEvent
    public void onChunkDataSave(ChunkDataEvent.Save event)
    {
        NBTTagCompound compound = event.getData();
        Chunk chunk = event.getChunk();

        if (copiedChunks.containsKey(ChunkCoordIntPair.chunkXZ2Int(chunk.xPosition, chunk.zPosition))) {
            compound.setBoolean("PerceptionCopy", true);
        }
    }
}
