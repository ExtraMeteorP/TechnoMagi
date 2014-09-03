package com.ollieread.technomagi.block;

import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockDisplacer extends BlockTM
{

    public BlockDisplacer(String name)
    {
        super(Material.iron, name);
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        float f = 16;
        HashSet hashset = new HashSet();
        int i;
        int j;
        int k;
        double d5;
        double d6;
        double d7;

        for (i = 0; i < 16; ++i) {
            for (j = 0; j < 16; ++j) {
                for (k = 0; k < 16; ++k) {
                    if (i == 0 || i == 16 - 1 || j == 0 || j == 16 - 1 || k == 0 || k == 16 - 1) {
                        double d0 = (double) ((float) i / ((float) 16 - 1.0F) * 2.0F - 1.0F);
                        double d1 = (double) ((float) j / ((float) 16 - 1.0F) * 2.0F - 1.0F);
                        double d2 = (double) ((float) k / ((float) 16 - 1.0F) * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f1 = 2.6F;
                        d5 = x;
                        d6 = y;
                        d7 = z;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F) {
                            int j1 = MathHelper.floor_double(d5);
                            int k1 = MathHelper.floor_double(d6);
                            int l1 = MathHelper.floor_double(d7);
                            Block block = world.getBlock(j1, k1, l1);

                            if (block.equals(Blocks.water)) {
                                world.setBlock(j1, k1, l1, com.ollieread.technomagi.common.init.Blocks.blockDisplacedAir);
                            }

                            d5 += d0 * (double) f2;
                            d6 += d1 * (double) f2;
                            d7 += d2 * (double) f2;
                        }
                    }
                }
            }
        }
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
