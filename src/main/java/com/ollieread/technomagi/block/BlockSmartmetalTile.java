package com.ollieread.technomagi.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.block.abstracts.BlockBasic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSmartmetalTile extends BlockBasic
{

    public BlockSmartmetalTile(String name)
    {
        super(Material.iron, name);

        setBlockUnbreakable();
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
    }

    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {

    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        if (rand.nextInt(100) == 0) {
            for (int l = 0; l < 4; ++l) {
                double d0 = (double) ((float) x + rand.nextFloat());
                double d1 = (double) ((float) y + rand.nextFloat());
                double d2 = (double) ((float) z + rand.nextFloat());
                double d3 = 0.0D;
                double d4 = 0.0D;
                double d5 = 0.0D;
                int i1 = rand.nextInt(2) * 2 - 1;
                d3 = ((double) rand.nextFloat() - 0.5D) * 0.2D;
                d4 = ((double) rand.nextFloat() - 0.5D) * 0.2D;
                d5 = ((double) rand.nextFloat() - 0.5D) * 0.2D;

                if (world.getBlock(x - 1, y, z) != this && world.getBlock(x + 1, y, z) != this) {
                    d0 = (double) x + 0.5D + 0.25D * (double) i1;
                    d3 = (double) (rand.nextFloat() * 2.0F * (float) i1);
                } else {
                    d2 = (double) z + 0.5D + 0.25D * (double) i1;
                    d5 = (double) (rand.nextFloat() * 2.0F * (float) i1);
                }

                EntityFX effect = new EntityPortalFX(world, d0, d1, d2, d3, d4, d5);
                effect.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

                Minecraft.getMinecraft().effectRenderer.addEffect(effect);
            }
        }
    }

}
