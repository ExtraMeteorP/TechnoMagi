package com.ollieread.technomagi.block;

import net.minecraft.block.material.Material;

import com.ollieread.technomagi.TechnoMagi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHardlightSlab extends BlockTM
{

    public BlockHardlightSlab(String name)
    {
        super(Material.rock, name);

        setLightOpacity(0);
        setBlockUnbreakable();
        setBlockName(name);
        setBlockTextureName("hardlight");
        setCreativeTab(TechnoMagi.tabTM);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
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

    public int getLightValue()
    {
        return 15;
    }

}
