package com.ollieread.technomagi.common.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.init.Items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockResource extends BlockSubtypes
{

    public BlockResource(String name)
    {
        super(name, new String[] { "etherium_ore", "voidstone", "copper_ore", "aluminium_ore", "copper_block", "aluminium_block" }, Material.rock);
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune)
    {
        return meta == 0 ? Items.crystal : Item.getItemFromBlock(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        if (world.getBlockMetadata(x, y, z) == 1) {
            if (rand.nextInt(100) == 0) {
                for (int l = 0; l < 4; ++l) {
                    double d0 = x + rand.nextFloat();
                    double d1 = y + rand.nextFloat();
                    double d2 = z + rand.nextFloat();
                    double d3 = 0.0D;
                    double d4 = 0.0D;
                    double d5 = 0.0D;
                    int i1 = rand.nextInt(2) * 2 - 1;
                    d3 = (rand.nextFloat() - 0.5D) * 0.5D;
                    d4 = (rand.nextFloat() - 0.5D) * 0.5D;
                    d5 = (rand.nextFloat() - 0.5D) * 0.5D;

                    if (world.getBlock(x - 1, y, z) != this && world.getBlock(x + 1, y, z) != this) {
                        d0 = x + 0.5D + 0.25D * i1;
                        d3 = rand.nextFloat() * 2.0F * i1;
                    } else {
                        d2 = z + 0.5D + 0.25D * i1;
                        d5 = rand.nextFloat() * 2.0F * i1;
                    }

                    world.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
                }
            }
        }
    }

}
