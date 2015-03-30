package com.ollieread.technomagi.common.block.structure;

import java.util.Random;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.World;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.tabs.TechnomagiTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHardlightFence extends BlockFence
{

    protected String name;

    public BlockHardlightFence(String name)
    {
        super(name, Material.glass);

        this.name = name;
        this.setCreativeTab(TechnomagiTabs.blocks);
        this.setBlockUnbreakable();
        this.setLightLevel(15F);
        this.setLightOpacity(0);
        this.setBlockTextureName("hardlight");
    }

    @Override
    public String getUnlocalizedName()
    {
        return "tile.technomagi." + name;
    }

    public void setBlockBoundsBasedOnState(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
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
    public int getRenderType()
    {
        return 11;
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
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Technomagi.MODID.toLowerCase() + ":" + getTextureName());
    }

}
