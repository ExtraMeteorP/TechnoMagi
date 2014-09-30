package com.ollieread.technomagi.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class OreGen implements IWorldGenerator
{

    private final Block oreBlock;
    protected int minGen = 0;
    protected int maxGen = 0;
    protected int amountVein = 0;
    protected int amountChunk = 0;

    public OreGen(Block block, int min, int max, int q, int c)
    {
        oreBlock = block;
        minGen = min;
        maxGen = max;
        amountVein = q;
        amountChunk = c;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if (world.provider instanceof WorldProviderSurface) {
            for (int i = 0; i < this.amountChunk; i++) {
                int x = chunkX * 16 + random.nextInt(16);
                int z = chunkZ * 16 + random.nextInt(16);
                int y = random.nextInt(Math.max(maxGen - minGen, 0)) + minGen;
                generateOre(world, random, x, y, z);
            }
        }
    }

    private boolean generateOre(World world, Random rand, int x, int y, int z)
    {
        float var6 = rand.nextFloat() * (float) Math.PI;
        double var7 = x + 8 + MathHelper.sin(var6) * amountVein / 8.0F;
        double var9 = x + 8 - MathHelper.sin(var6) * amountVein / 8.0F;
        double var11 = z + 8 + MathHelper.cos(var6) * amountVein / 8.0F;
        double var13 = z + 8 - MathHelper.cos(var6) * amountVein / 8.0F;
        double var15 = y + rand.nextInt(3) - 2;
        double var17 = y + rand.nextInt(3) - 2;

        for (int var19 = 0; var19 <= amountVein; ++var19) {
            double var20 = var7 + (var9 - var7) * var19 / amountVein;
            double var22 = var15 + (var17 - var15) * var19 / amountVein;
            double var24 = var11 + (var13 - var11) * var19 / amountVein;
            double var26 = rand.nextDouble() * amountVein / 16.0D;
            double var28 = (MathHelper.sin(var19 * (float) Math.PI / amountVein) + 1.0F) * var26 + 1.0D;
            double var30 = (MathHelper.sin(var19 * (float) Math.PI / amountVein) + 1.0F) * var26 + 1.0D;
            int var32 = MathHelper.floor_double(var20 - var28 / 2.0D);
            int var33 = MathHelper.floor_double(var22 - var30 / 2.0D);
            int var34 = MathHelper.floor_double(var24 - var28 / 2.0D);
            int var35 = MathHelper.floor_double(var20 + var28 / 2.0D);
            int var36 = MathHelper.floor_double(var22 + var30 / 2.0D);
            int var37 = MathHelper.floor_double(var24 + var28 / 2.0D);

            for (int var38 = var32; var38 <= var35; ++var38) {
                double var39 = (var38 + 0.5D - var20) / (var28 / 2.0D);

                if (var39 * var39 < 1.0D) {
                    for (int var41 = var33; var41 <= var36; ++var41) {
                        double var42 = (var41 + 0.5D - var22) / (var30 / 2.0D);

                        if (var39 * var39 + var42 * var42 < 1.0D) {
                            for (int var44 = var34; var44 <= var37; ++var44) {
                                double var45 = (var44 + 0.5D - var24) / (var28 / 2.0D);

                                Block block = world.getBlock(var38, var41, var44);
                                if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0D && block == Blocks.stone) {
                                    world.setBlock(var38, var41, var44, this.oreBlock);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
