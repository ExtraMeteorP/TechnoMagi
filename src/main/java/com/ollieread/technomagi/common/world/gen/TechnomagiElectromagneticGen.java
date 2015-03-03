package com.ollieread.technomagi.common.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket;
import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket.EnergyType;
import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket.PocketSize;
import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocketManager;
import com.ollieread.technomagi.common.block.electromagnetic.tile.TileElectromagnetic;

import cpw.mods.fml.common.IWorldGenerator;

public class TechnomagiElectromagneticGen implements IWorldGenerator
{

    private final Block pocketBlock;
    protected int minGen = 0;
    protected int maxGen = 0;
    protected int amountVein = 3;
    protected int amountChunk = 6;

    public TechnomagiElectromagneticGen(Block block, int min, int max)
    {
        pocketBlock = block;
        minGen = min;
        maxGen = max;
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

                                int xCoord = var38;
                                int yCoord = var41;
                                int zCoord = var44;

                                Block block = world.getBlock(xCoord, yCoord, zCoord);
                                int metadata = world.getBlockMetadata(xCoord, yCoord, zCoord);

                                if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0D) {
                                    if (!block.hasTileEntity(metadata) && block != Blocks.bedrock) {
                                        ElectromagneticPocketManager manager = ElectromagneticPocketManager.get(world);

                                        if (manager != null) {
                                            ElectromagneticPocket pocket = manager.getNearestPocket(xCoord, yCoord, zCoord);
                                            PocketSize newSize = null;
                                            int radius = 0;

                                            if (pocket != null) {
                                                ChunkCoordinates coords = pocket.coordinates;
                                                PocketSize size = pocket.size;

                                                float distance = coords.getDistanceSquared(xCoord, yCoord, zCoord);

                                                if (distance <= (128 * 128)) {
                                                    continue;
                                                }

                                                if (distance > (32 * 32) && distance < (64 * 64)) {
                                                    radius = 33 + rand.nextInt(32);
                                                } else if (distance <= (32 * 32) && distance > (16 * 16)) {
                                                    radius = 17 + rand.nextInt(16);
                                                } else {
                                                    radius = 8 + rand.nextInt(8);
                                                }
                                            } else {
                                                radius = 8 + rand.nextInt(56);
                                                newSize = PocketSize.getPocketSize(radius);
                                            }

                                            BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(xCoord, zCoord);
                                            EnergyType newType = EnergyType.values()[rand.nextInt(EnergyType.values().length)];

                                            world.setBlock(xCoord, yCoord, zCoord, this.pocketBlock, 0, 2);

                                            TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);

                                            if (tile != null && tile instanceof TileElectromagnetic) {
                                                ((TileElectromagnetic) tile).set(newType, radius, rand.nextInt(2) == 0 ? false : true);
                                                manager.addPocket(((TileElectromagnetic) tile));
                                            }
                                        }
                                    }
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
