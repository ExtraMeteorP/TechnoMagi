package com.ollieread.technomagi.block;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.World;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHardlightFence extends BlockFence
{

    public BlockHardlightFence(String name)
    {
        super(name, Material.rock);

        setLightOpacity(0);
        setBlockUnbreakable();
        setBlockName(name);
        setBlockTextureName("hardlight");
        setCreativeTab(TechnoMagi.tabTM);

        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    public void setBlockBoundsBasedOnState(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean canSilkHarvest()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    public int getRenderType()
    {
        return 11;
    }

    public int getLightValue()
    {
        return 15;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

}
