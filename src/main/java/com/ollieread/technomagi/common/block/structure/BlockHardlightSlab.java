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

public class BlockHardlightSlab extends BlockSlab
{

    protected String name;

    public BlockHardlightSlab(String name)
    {
        super(false, Material.glass);

        this.name = name;
        this.setCreativeTab(TechnomagiTabs.blocks);
        this.setBlockUnbreakable();
        this.setLightLevel(15F);
        this.setLightOpacity(0);
        this.setBlockTextureName(Technomagi.MODID.toLowerCase() + ":hardlight");
    }

    public String getTexturePath(String name)
    {
        return Technomagi.MODID.toLowerCase() + ":" + name;
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
