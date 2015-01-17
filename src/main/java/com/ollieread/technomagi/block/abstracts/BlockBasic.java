package com.ollieread.technomagi.block.abstracts;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockBasic extends Block
{

    public BlockBasic(Material material, String name)
    {
        super(material);

        setBlockName(name);
        setBlockTextureName(name);
        setCreativeTab(TechnoMagi.tabTM);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

    public int quantityDropped(Random random)
    {
        return 1;
    }

}
