package com.ollieread.technomagi.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class GenerationHelper
{

    /**
     * This bit of code is a modified version of the method of the same name,
     * from WorldEdit, a Minecraft world manipulation toolkit, by sk89q and the
     * WorldEdit team
     * 
     * Copyright (C) sk89q <http://www.sk89q.com>
     * 
     * Copyright (C) WorldEdit team and contributors
     * 
     * @param pos
     * @param block
     * @param world
     * @param radiusX
     * @param radiusY
     * @param radiusZ
     * @param filled
     * @return
     */
    public static List getSphere(Vec3 pos, World world, double radiusX, double radiusY, double radiusZ, boolean filled)
    {
        HashSet hashset = new HashSet();

        radiusX += 0.5;
        radiusY += 0.5;
        radiusZ += 0.5;

        final double invRadiusX = 1 / radiusX;
        final double invRadiusY = 1 / radiusY;
        final double invRadiusZ = 1 / radiusZ;

        final int ceilRadiusX = (int) Math.ceil(radiusX);
        final int ceilRadiusY = (int) Math.ceil(radiusY);
        final int ceilRadiusZ = (int) Math.ceil(radiusZ);

        double nextXn = 0;
        forX: for (int x = 0; x <= ceilRadiusX; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadiusX;
            double nextYn = 0;
            forY: for (int y = 0; y <= ceilRadiusY; ++y) {
                final double yn = nextYn;
                nextYn = (y + 1) * invRadiusY;
                double nextZn = 0;
                forZ: for (int z = 0; z <= ceilRadiusZ; ++z) {
                    final double zn = nextZn;
                    nextZn = (z + 1) * invRadiusZ;

                    double distanceSq = lengthSq(xn, yn, zn);
                    if (distanceSq > 1) {
                        if (z == 0) {
                            if (y == 0) {
                                break forX;
                            }
                            break forY;
                        }
                        break forZ;
                    }

                    if (!filled) {
                        if (lengthSq(nextXn, yn, zn) <= 1 && lengthSq(xn, nextYn, zn) <= 1 && lengthSq(xn, yn, nextZn) <= 1) {
                            continue;
                        }
                    }

                    hashset.add(new ChunkPosition((int) pos.xCoord + x, (int) pos.yCoord + y, (int) pos.zCoord + z));
                    hashset.add(new ChunkPosition((int) pos.xCoord - x, (int) pos.yCoord + y, (int) pos.zCoord + z));
                    hashset.add(new ChunkPosition((int) pos.xCoord + x, (int) pos.yCoord - y, (int) pos.zCoord + z));
                    hashset.add(new ChunkPosition((int) pos.xCoord + x, (int) pos.yCoord + y, (int) pos.zCoord - z));
                    hashset.add(new ChunkPosition((int) pos.xCoord - x, (int) pos.yCoord - y, (int) pos.zCoord + z));
                    hashset.add(new ChunkPosition((int) pos.xCoord + x, (int) pos.yCoord - y, (int) pos.zCoord - z));
                    hashset.add(new ChunkPosition((int) pos.xCoord - x, (int) pos.yCoord + y, (int) pos.zCoord - z));
                    hashset.add(new ChunkPosition((int) pos.xCoord - x, (int) pos.yCoord - y, (int) pos.zCoord - z));
                }
            }
        }

        List affectedBlocks = new ArrayList();
        affectedBlocks.addAll(hashset);

        return affectedBlocks;
    }

    private static double lengthSq(double x, double y, double z)
    {
        return (x * x) + (y * y) + (z * z);
    }

    private static double lengthSq(double x, double z)
    {
        return (x * x) + (z * z);
    }

}
