package com.ollieread.technomagi.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHelper
{

    public static Block[] getNeighbours(World world, int x, int y, int z)
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
        Block[] blocks = new Block[directions.length];

        for (int i = 0; i < directions.length; i++) {
            blocks[i] = world.getBlock(directions[i].offsetX, directions[i].offsetY, directions[i].offsetZ);
        }

        return blocks;
    }

}
