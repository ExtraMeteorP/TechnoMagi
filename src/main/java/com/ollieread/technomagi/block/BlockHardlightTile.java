package com.ollieread.technomagi.block;

import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHardlightTile extends BlockTM
{

    public BlockHardlightTile(String name)
    {
        super(Material.rock, name);

        setLightOpacity(0);
        setBlockUnbreakable();
        setBlockTextureName("hardlight");
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
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
