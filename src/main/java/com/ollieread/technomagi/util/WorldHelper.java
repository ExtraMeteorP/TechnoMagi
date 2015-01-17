package com.ollieread.technomagi.util;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldHelper
{

    public static Block[] getNeighbourBlocks(World world, int x, int y, int z)
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
        Block[] blocks = new Block[directions.length];

        for (int i = 0; i < directions.length; i++) {
            blocks[i] = world.getBlock(directions[i].offsetX, directions[i].offsetY, directions[i].offsetZ);
        }

        return blocks;
    }

    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        double d3 = x1 - x2;
        double d4 = y1 - y2;
        double d5 = z1 - z2;
        return (double) MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
    }

    public static List getTileEntitiesWithin(World world, int x1, int y1, int z1, int x2, int y2, int z2)
    {
        WorldServer worldServer = MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId);

        return worldServer.func_147486_a(x1, y1, z1, x2, y2, z2);
    }
}
