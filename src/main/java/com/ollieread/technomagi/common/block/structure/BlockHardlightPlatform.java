package com.ollieread.technomagi.common.block.structure;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.tabs.TechnomagiTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHardlightPlatform extends BlockSlab
{

    protected String name;

    public BlockHardlightPlatform(String name)
    {
        super(false, Material.glass);

        this.name = name;
        this.setCreativeTab(TechnomagiTabs.blocks);
        this.setBlockUnbreakable();
        this.setLightLevel(15F);
        this.setLightOpacity(0);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
        this.setBlockTextureName(Technomagi.MODID.toLowerCase() + ":hardlight");
    }

    @Override
    public String getUnlocalizedName()
    {
        return "tile.technomagi." + name;
    }

    @Override
    public String func_150002_b(int meta)
    {
        return getUnlocalizedName();
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        if (this.field_150004_a) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            boolean flag = (world.getBlockMetadata(x, y, z) & 8) != 0;

            if (flag) {
                this.setBlockBounds(0.0F, 0.9375F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
            }
        }
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        if (this.field_150004_a) {
            this.setBlockBounds(0.0F, 0.9375F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean canSilkHarvest()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public int getLightValue()
    {
        return 15;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        Block block = world.getBlock(x, y, z);

        return block == this ? false : true;
    }

}
