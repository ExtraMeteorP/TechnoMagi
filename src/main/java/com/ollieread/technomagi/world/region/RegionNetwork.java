package com.ollieread.technomagi.world.region;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class RegionNetwork
{

    protected int id;
    protected int[] start;
    protected int[] end;

    public RegionNetwork(int id, int[] start, int[] end)
    {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public int getId()
    {
        return id;
    }

    public int getChunkCount()
    {
        return ((end[0] - start[0]) / 16) * ((end[1] - start[1]) / 16);
    }

    public Chunk[] getChunks(World world)
    {
        int count = getChunkCount();
        Chunk[] chunks = new Chunk[count];

        int x = start[0];
        int z = start[1];

        for (int i = 0; i < chunks.length; i++) {
            chunks[i] = world.getChunkFromChunkCoords(x, z);

            x += 15;
            z += 15;
        }

        return chunks;
    }

    public Long[] getChunkPairs()
    {
        int count = getChunkCount();
        Long[] chunks = new Long[count];

        int x = start[0];
        int z = start[1];

        for (int i = 0; i < chunks.length; i++) {
            chunks[i] = ChunkCoordIntPair.chunkXZ2Int(x, z);

            x += 15;
            z += 15;
        }

        return chunks;
    }

    public boolean isInside(int x, int z)
    {
        return x >= start[0] && x <= end[0] && z >= start[1] && z <= end[1];
    }

    public void sync(NBTTagCompound compound)
    {
        compound.setInteger("ID", id);
        compound.setIntArray("Start", start);
        compound.setIntArray("End", end);
    }

}
