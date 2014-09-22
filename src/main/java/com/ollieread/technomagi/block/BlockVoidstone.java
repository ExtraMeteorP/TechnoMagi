package com.ollieread.technomagi.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockVoidstone extends BlockTM
{

    public BlockVoidstone(String name)
    {
        super(Material.rock, name);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        float f = (float) x + 0.5F;
        float f1 = (float) y + 0.0F + rand.nextFloat() * 6.0F / 16.0F;
        float f2 = (float) z + 0.5F;
        float f3 = 0.52F;
        float f4 = rand.nextFloat() * 0.6F - 0.3F;

        world.spawnParticle("suspended", (double) (f - f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
        world.spawnParticle("suspended", (double) (f + f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
        world.spawnParticle("suspended", (double) (f + f4), (double) f1, (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
        world.spawnParticle("suspended", (double) (f + f4), (double) f1, (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
    }

}
