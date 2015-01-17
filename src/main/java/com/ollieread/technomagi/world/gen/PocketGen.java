package com.ollieread.technomagi.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class PocketGen implements IWorldGenerator
{

    private final Block pocketBlock;
    protected int minGen = 0;
    protected int maxGen = 0;
    protected int amountVein = 1;
    protected int amountChunk = 1;

    public PocketGen(Block block, int min, int max)
    {
        pocketBlock = block;
        minGen = min;
        maxGen = max;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        for (int i = 0; i < this.amountChunk; i++) {
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);
            int y = random.nextInt(Math.max(maxGen - minGen, 0)) + minGen;
        }
    }

}
