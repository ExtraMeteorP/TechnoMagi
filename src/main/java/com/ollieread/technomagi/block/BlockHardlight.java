package com.ollieread.technomagi.block;

import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHardlight extends BlockTM
{

    public BlockHardlight(String name)
    {
        super(Material.rock, name);

        setLightOpacity(0);
        setBlockUnbreakable();
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
