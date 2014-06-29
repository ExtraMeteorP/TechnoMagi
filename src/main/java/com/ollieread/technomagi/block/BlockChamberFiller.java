package com.ollieread.technomagi.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChamberFiller extends Block
{

    public BlockChamberFiller(String name)
    {
        super(Material.iron);

        setBlockTextureName("invisible");
        setBlockName(name);
        setCreativeTab(null);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        int meta = world.getBlockMetadata(x, y, z);

        if (meta == 0) {
            Blocks.blockObservationChamber.onBlockActivated(world, x, y - 1, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
        } else if (meta == 1) {
            Blocks.blockObservationChamber.onBlockActivated(world, x, y - 2, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
        }

        return false;
    }

}
