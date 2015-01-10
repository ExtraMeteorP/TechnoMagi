package com.ollieread.technomagi.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockPortal;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class ClairvoyanceHelper
{

    public static boolean canBeNetherPortal(World world, int x, int y, int z)
    {
        BlockPortal.Size size = new BlockPortal.Size(world, x, y, z, 1);
        BlockPortal.Size size1 = new BlockPortal.Size(world, x, y, z, 2);
        boolean flag = true;
        int portalCount = 0;
        int portalCount2 = 0;

        for (Field f : BlockPortal.Size.class.getDeclaredFields()) {
            f.setAccessible(true);

            try {
                if (f.getName().equals("field_150864_e")) {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

                    portalCount = (Integer) f.get(size);
                    portalCount2 = (Integer) f.get(size1);
                }
            } catch (Exception e) {
                System.err.println("Severe error, please report this to the mod author");
                System.err.println(e);
            }
        }

        if (size.func_150860_b() && portalCount == 0) {
            return true;
        } else if (size1.func_150860_b() && portalCount2 == 0) {
            return true;
        }

        return false;
    }

    public static boolean canBeEndPortal(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        int i1 = world.getBlockMetadata(x, y, z);
        Random itemRand = new Random();

        if (block == Blocks.end_portal_frame && !BlockEndPortalFrame.isEnderEyeInserted(i1)) {
            int j1;

            j1 = i1 & 3;
            int j2 = 0;
            int k1 = 0;
            boolean flag1 = false;
            boolean flag = true;
            int k2 = Direction.rotateRight[j1];
            int l1;
            int i2;
            int l2;

            for (l1 = -2; l1 <= 2; ++l1) {
                l2 = x + Direction.offsetX[k2] * l1;
                i2 = z + Direction.offsetZ[k2] * l1;

                if (world.getBlock(l2, y, i2) == Blocks.end_portal_frame) {
                    if (!BlockEndPortalFrame.isEnderEyeInserted(world.getBlockMetadata(l2, y, i2))) {
                        flag = false;
                        break;
                    }

                    k1 = l1;

                    if (!flag1) {
                        j2 = l1;
                        flag1 = true;
                    }
                }
            }

            if (flag && k1 == j2 + 2) {
                for (l1 = j2; l1 <= k1; ++l1) {
                    l2 = x + Direction.offsetX[k2] * l1;
                    i2 = z + Direction.offsetZ[k2] * l1;
                    l2 += Direction.offsetX[j1] * 4;
                    i2 += Direction.offsetZ[j1] * 4;

                    if (world.getBlock(l2, y, i2) != Blocks.end_portal_frame || !BlockEndPortalFrame.isEnderEyeInserted(world.getBlockMetadata(l2, y, i2))) {
                        flag = false;
                        break;
                    }
                }

                int i3;

                for (l1 = j2 - 1; l1 <= k1 + 1; l1 += 4) {
                    for (l2 = 1; l2 <= 3; ++l2) {
                        i2 = x + Direction.offsetX[k2] * l1;
                        i3 = z + Direction.offsetZ[k2] * l1;
                        i2 += Direction.offsetX[j1] * l2;
                        i3 += Direction.offsetZ[j1] * l2;

                        if (world.getBlock(i2, y, i3) != Blocks.end_portal_frame || !BlockEndPortalFrame.isEnderEyeInserted(world.getBlockMetadata(i2, y, i3))) {
                            flag = false;
                            break;
                        }
                    }
                }

                return flag;
            }
        }

        return false;
    }

}
